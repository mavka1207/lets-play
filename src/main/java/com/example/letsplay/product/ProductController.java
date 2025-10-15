package com.example.letsplay.product;


import com.example.letsplay.product.dto.CreateProductRequest;
import com.example.letsplay.product.dto.ProductResponse;
import com.example.letsplay.product.dto.UpdateProductRequest;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductController {
private final ProductService service;
public ProductController(ProductService service) { this.service = service; }


@GetMapping
public List<ProductResponse> list() {
return service.all().stream().map(p -> new ProductResponse(p.getId(), p.getName(), p.getDescription(), p.getPrice(), p.getUserId())).toList();
}


@GetMapping("/{id}")
public ProductResponse one(@PathVariable String id) {
var p = service.byId(id);
return new ProductResponse(p.getId(), p.getName(), p.getDescription(), p.getPrice(), p.getUserId());
}


@PostMapping
@PreAuthorize("isAuthenticated()")
public ProductResponse create(@AuthenticationPrincipal(expression = "id") String userId,
@RequestBody @Valid CreateProductRequest req) {
var p = service.create(userId, req);
return new ProductResponse(p.getId(), p.getName(), p.getDescription(), p.getPrice(), p.getUserId());
}


@PutMapping("/{id}")
@PreAuthorize("hasRole('ADMIN') or @productSecurity.isOwner(#id, authentication.principal.id)")
public ProductResponse update(@PathVariable String id, @RequestBody @Valid UpdateProductRequest req) {
var p = service.update(id, req);
return new ProductResponse(p.getId(), p.getName(), p.getDescription(), p.getPrice(), p.getUserId());
}


@DeleteMapping("/{id}")
@PreAuthorize("hasRole('ADMIN') or @productSecurity.isOwner(#id, authentication.principal.id)")
public void delete(@PathVariable String id) { service.delete(id); }
}
