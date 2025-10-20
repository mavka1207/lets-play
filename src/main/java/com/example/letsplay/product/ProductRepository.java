package com.example.letsplay.product;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

/** Mongo repository for products. */
public interface ProductRepository extends MongoRepository<Product, String> {
  List<Product> findByUserId(String userId);
}
