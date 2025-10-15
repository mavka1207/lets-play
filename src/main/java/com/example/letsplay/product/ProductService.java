package com.example.letsplay.product;


import com.example.letsplay.product.dto.CreateProductRequest;
import com.example.letsplay.product.dto.UpdateProductRequest;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class ProductService {
private final ProductRepository repo;
public ProductService(ProductRepository repo) { this.repo = repo; }


public List<Product> all() { return repo.findAll(); }
public Product byId(String id) { return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found")); }
public Product create(String ownerId, CreateProductRequest req) {
Product p = new Product();
p.setName(req.getName());
p.setDescription(req.getDescription());
p.setPrice(req.getPrice());
p.setUserId(ownerId);
return repo.save(p);
}
public Product update(String id, UpdateProductRequest req) {
Product p = byId(id);
if (req.getName() != null) p.setName(req.getName());
if (req.getDescription() != null) p.setDescription(req.getDescription());
if (req.getPrice() != null) p.setPrice(req.getPrice());
return repo.save(p);
}
public void delete(String id) { repo.deleteById(id); }
}
