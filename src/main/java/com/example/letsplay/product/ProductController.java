package com.example.letsplay.product;

import com.example.letsplay.user.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

  private final ProductService service;

  public ProductController(ProductService service) {
    this.service = service;
  }

  // Public GET
  @GetMapping
  public List<ProductResponse> list() {
    return service.list().stream().map(ProductResponse::from).toList();
  }

  // Public GET by id
  @GetMapping("/{id}")
  public ProductResponse get(@PathVariable String id) {
    return ProductResponse.from(service.getById(id));
  }

  // Create (auth only), userId берём из principal
  @PostMapping
  public ResponseEntity<ProductResponse> create(
      @Valid @RequestBody CreateProductRequest req,
      @AuthenticationPrincipal User principal
  ) {
    Product p = new Product();
    p.setName(req.getName());
    p.setDescription(req.getDescription());
    p.setPrice(req.getPrice());
    p.setUserId(principal.getId()); // критично: userId из токена

    Product saved = service.create(p);
    return ResponseEntity
        .created(URI.create("/products/" + saved.getId()))
        .body(ProductResponse.from(saved));
  }

  // Update (owner or ADMIN), патч с DTO
  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN') or @ownership.isProductOwner(#id, principal.id)")
  public ProductResponse update(
      @PathVariable String id,
      @Valid @RequestBody UpdateProductRequest req
  ) {
    Product patch = new Product();
    patch.setName(req.getName());
    patch.setDescription(req.getDescription());
    patch.setPrice(req.getPrice());
    Product updated = service.update(id, patch);
    return ProductResponse.from(updated);
  }

  // Delete (owner or ADMIN)
  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN') or @ownership.isProductOwner(#id, principal.id)")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    service.delete(id);
    return ResponseEntity.ok().build();
  }
}
