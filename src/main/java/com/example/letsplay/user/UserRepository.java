package com.example.letsplay.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

/** Mongo repository for users. */
public interface UserRepository extends MongoRepository<User, String> {
  Optional<User> findByEmail(String email);
}
