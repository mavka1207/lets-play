package com.example.letsplay.auth;


import com.example.letsplay.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


private final JwtService jwtService;
private final UserRepository userRepository;


public JwtAuthenticationFilter(JwtService jwtService, UserRepository userRepository) {
this.jwtService = jwtService;
this.userRepository = userRepository;
}


@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
throws ServletException, IOException {
final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
if (authHeader == null || !authHeader.startsWith("Bearer ")) {
chain.doFilter(request, response);
return;
}
String jwt = authHeader.substring(7);
String email;
try {
email = jwtService.extractUsername(jwt);
} catch (Exception ex) {
chain.doFilter(request, response); // invalid token -> proceed without auth
return;
}
if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
var user = userRepository.findByEmail(email).orElse(null);
if (user != null) {
var authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
SecurityContextHolder.getContext().setAuthentication(authToken);
}
}
chain.doFilter(request, response);
}
}
