package com.example.letsplay.auth;


import com.example.letsplay.user.dto.LoginRequest;
import com.example.letsplay.user.dto.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;


@RestController
@RequestMapping("/auth")
public class AuthController {


private final AuthService authService;


public AuthController(AuthService authService) {
this.authService = authService;
}


@PostMapping("/register")
public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest req, @RequestParam(name="admin", defaultValue = "false") boolean admin) {
String jwt = authService.register(req, admin);
return ResponseEntity.ok(Map.of("token", jwt));
}


@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody @Valid LoginRequest req) {
String jwt = authService.login(req);
return ResponseEntity.ok(Map.of("token", jwt));
}
}
