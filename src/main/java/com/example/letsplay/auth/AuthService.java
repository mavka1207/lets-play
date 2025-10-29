package com.example.letsplay.auth;

//import com.example.letsplay.user.Role;
import com.example.letsplay.user.User;
import com.example.letsplay.user.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//import java.util.NoSuchElementException;

/** Creates users, validates credentials, issues JWTs. */
@Service
public class AuthService {

  private final UserRepository users;
  private final PasswordEncoder encoder;
  private final JwtService jwt;
  
  @Autowired
  public AuthService(UserRepository users, PasswordEncoder encoder, JwtService jwt) {
    this.users = users; this.encoder = encoder; this.jwt = jwt;
  }

  public String register(String name, String email, String rawPassword, String roleStr) {
    users.findByEmail(email).ifPresent(u -> { throw new IllegalArgumentException("Email already in use"); });
    User u = new User();
    u.setName(name);
    u.setEmail(email);
    u.setPassword(encoder.encode(rawPassword));
    users.save(u);
    return jwt.generateToken(u);
  }

  public String login(String email, String rawPassword) {
    User u = users.findByEmail(email).orElseThrow(() -> new BadCredentialsException("Invalid credentials"));
    if (!encoder.matches(rawPassword, u.getPassword())) {
      throw new BadCredentialsException("Invalid credentials");
    }
    return jwt.generateToken(u);
  }
}

// AuthenticationException