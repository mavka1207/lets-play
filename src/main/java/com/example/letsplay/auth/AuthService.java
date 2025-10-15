package com.example.letsplay.auth;


import com.example.letsplay.user.Role;
import com.example.letsplay.user.User;
import com.example.letsplay.user.UserRepository;
import com.example.letsplay.user.dto.LoginRequest;
import com.example.letsplay.user.dto.RegisterRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AuthService {
private final UserRepository userRepo;
private final PasswordEncoder encoder;
private final AuthenticationManager authManager;
private final JwtService jwtService;


public AuthService(UserRepository userRepo, PasswordEncoder encoder, AuthenticationManager authManager, JwtService jwtService) {
this.userRepo = userRepo;
this.encoder = encoder;
this.authManager = authManager;
this.jwtService = jwtService;
}


@Transactional
public String register(RegisterRequest req, boolean asAdmin) {
if (userRepo.existsByEmail(req.getEmail())) {
throw new IllegalArgumentException("Email already in use");
}
User u = new User();
u.setName(req.getName());
u.setEmail(req.getEmail());
u.setPassword(encoder.encode(req.getPassword()));
u.setRole(asAdmin ? Role.ADMIN : Role.USER);
userRepo.save(u);
return jwtService.generateToken(u);
}


public String login(LoginRequest req) {
var token = new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword());
authManager.authenticate(token);
var user = userRepo.findByEmail(req.getEmail()).orElseThrow();
return jwtService.generateToken(user);
}
}
