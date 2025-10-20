package com.example.letsplay.user;

import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/** Application service for user management (admin-facing helpers). */
@Service
public class UserService {

  private final UserRepository repo;
  private final PasswordEncoder encoder;

  public UserService(UserRepository repo, PasswordEncoder encoder) {
    this.repo = repo;
    this.encoder = encoder;
  }

  public List<User> list() {
    return repo.findAll();
  }

  public User getById(String id) {
    return repo.findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));
  }

  public User create(@Valid User u) {
    u.setPassword(encoder.encode(u.getPassword()));
    if (u.getRole() == null) u.setRole(Role.USER);
    return repo.save(u);
  }

  public void delete(String id) {
    repo.deleteById(id);
  }
}
