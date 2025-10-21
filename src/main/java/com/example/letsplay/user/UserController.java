package com.example.letsplay.user;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Admin-only endpoints for user management.
 * Regular users register/login via /auth endpoints.
 */
@RestController
@RequestMapping("/users")

public class UserController {

  private final UserService service;

  public UserController(UserService service) { this.service = service; }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public List<User> list() {
    return service.list();
  }

  @GetMapping("/me")
  public User getMe() {
    return service.getCurrentUser();
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public User get(@PathVariable String id) {
    return service.getById(id);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public User create(@Valid @RequestBody User u) {
    return service.create(u);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public void delete(@PathVariable String id) {
    service.delete(id);
  }
  @DeleteMapping("/me")
  public void deleteMe(@AuthenticationPrincipal User principal) {
    service.deleteCurrentUser();
  }
}
