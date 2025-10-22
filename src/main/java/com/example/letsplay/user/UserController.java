package com.example.letsplay.user;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.example.letsplay.auth.JwtService;
import com.example.letsplay.user.dto.UpdateUserRequest;
import com.example.letsplay.user.dto.UserResponse;

import java.util.Map;

import java.util.List;

/**
 * Admin-only endpoints for user management.
 * Regular users register/login via /auth endpoints.
 */
@RestController
@RequestMapping("/users")

public class UserController {

  private final UserService service;
  private JwtService jwtService;

  public UserController(UserService service, JwtService jwtService) {
    this.service = service;
    this.jwtService = jwtService;
  }
@PutMapping("/me")
public Map<String, Object> updateMe(@Valid @RequestBody UpdateUserRequest req) {
  User updated = service.updateCurrentUser(req);
  String newToken = jwtService.generateToken(updated);
  return Map.of(
      "user", UserResponse.from(updated),
      "token", newToken
  );
} 

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
