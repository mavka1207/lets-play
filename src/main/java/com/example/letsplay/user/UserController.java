package com.example.letsplay.user;


import com.example.letsplay.user.dto.UpdateUserRequest;
import com.example.letsplay.user.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {
private final UserService service;
public UserController(UserService service) { this.service = service; }


@GetMapping
@PreAuthorize("hasRole('ADMIN')")
public List<UserResponse> all() {
return service.findAll().stream()
.map(u -> new UserResponse(u.getId(), u.getName(), u.getEmail(), u.getRole().name()))
.toList();
}


@GetMapping("/{id}")
@PreAuthorize("hasRole('ADMIN')")
public UserResponse one(@PathVariable String id) {
var u = service.findById(id);
return new UserResponse(u.getId(), u.getName(), u.getEmail(), u.getRole().name());
}


@PutMapping("/{id}")
@PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
public UserResponse update(@PathVariable String id, @RequestBody @Valid UpdateUserRequest req) {
var u = service.update(id, req);
return new UserResponse(u.getId(), u.getName(), u.getEmail(), u.getRole().name());
}


@DeleteMapping("/{id}")
@PreAuthorize("hasRole('ADMIN')")
public void delete(@PathVariable String id) { service.delete(id); }
}
