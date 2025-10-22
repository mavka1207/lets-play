package com.example.letsplay.security;

import com.example.letsplay.product.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Ownership helper used in @PreAuthorize expressions.
 * Example: @PreAuthorize("hasRole('ADMIN') or @ownership.isProductOwner(#id)")
 */
@Component("ownership")
public class OwnershipChecker {

  private final ProductRepository repo;
  
  @Autowired
  public OwnershipChecker(ProductRepository repo) {
    this.repo = repo;
  }

  public boolean isProductOwner(String productId, String userId) {
    if (productId == null || userId == null) return false;
    return repo.findById(productId).map(p -> userId.equals(p.getUserId())).orElse(false);
  }
}
