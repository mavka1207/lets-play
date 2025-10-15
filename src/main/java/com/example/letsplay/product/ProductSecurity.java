package com.example.letsplay.product;


import org.springframework.stereotype.Component;


@Component("productSecurity")
public class ProductSecurity {
private final ProductRepository repo;
public ProductSecurity(ProductRepository repo) { this.repo = repo; }
public boolean isOwner(String productId, String userId) {
return repo.findById(productId).map(p -> p.getUserId().equals(userId)).orElse(false);
}
}