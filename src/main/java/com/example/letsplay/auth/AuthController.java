package com.example.letsplay.auth;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/** Public authentication endpoints (register/login). */
@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthService auth;

  @Autowired
  public AuthController(AuthService auth) { this.auth = auth; }

  @PostMapping("/register")
  public Map<String, String> register(@Valid @RequestBody RegisterRequest req) {
    String token = auth.register(req.getName(), req.getEmail(), req.getPassword());
    return Map.of("token", token);
  }

  @PostMapping("/login")
  public Map<String, String> login(@Valid @RequestBody LoginRequest req) {
    String token = auth.login(req.getEmail(), req.getPassword());
    return Map.of("token", token);
  }
}
