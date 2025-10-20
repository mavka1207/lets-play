package com.example.letsplay.product;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {

  private final ProductRepository repo;

  public ProductService(ProductRepository repo) {
    this.repo = repo;
  }

  public List<Product> list() {
    return repo.findAll();
  }

  public Product getById(String id) {
    return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Product not found"));
  }

  public Product create(Product p) {
    // Валидацию полей делаем на DTO в контроллере
    return repo.save(p);
  }

  public Product update(String id, Product patch) {
    Product existing = getById(id);
    if (patch.getName() != null) existing.setName(patch.getName());
    if (patch.getDescription() != null) existing.setDescription(patch.getDescription());
    if (patch.getPrice() != null) existing.setPrice(patch.getPrice());
    return repo.save(existing);
  }

  public void delete(String id) {
    repo.deleteById(id);
  }
}
