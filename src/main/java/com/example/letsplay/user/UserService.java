package com.example.letsplay.user;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.letsplay.user.dto.UpdateUserRequest;

import java.util.List;
import java.util.NoSuchElementException;

/** Application service for user management (admin-facing helpers). */
@Service
public class UserService {

  private final UserRepository repo;
  private final PasswordEncoder encoder;

  @Autowired
  public UserService(UserRepository repo, PasswordEncoder encoder) {
    this.repo = repo;
    this.encoder = encoder;
  }

  public User getCurrentUser() {
      return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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

   public void deleteCurrentUser() {
        User currentUser = getCurrentUser();
        delete(currentUser.getId());
    }

  public void delete(String id) {
    repo.deleteById(id);
  }

  public User updateCurrentUser(@Valid UpdateUserRequest req) {
    User currentUser = getCurrentUser();
    if (req.getName() != null) currentUser.setName(req.getName());
    if (req.getEmail() != null) currentUser.setEmail(req.getEmail());
    if (req.getPassword() != null) currentUser.setPassword(encoder.encode(req.getPassword()));
    return repo.save(currentUser);
  }
}
