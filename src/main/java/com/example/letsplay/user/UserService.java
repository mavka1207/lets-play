package com.example.letsplay.user;


import com.example.letsplay.user.dto.UpdateUserRequest;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class UserService {
private final UserRepository repo;
public UserService(UserRepository repo) { this.repo = repo; }


public List<User> findAll() { return repo.findAll(); }
public User findById(String id) { return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found")); }
public User update(String id, UpdateUserRequest req) {
User u = findById(id);
if (req.getName() != null) u.setName(req.getName());
return repo.save(u);
}
public void delete(String id) { repo.deleteById(id); }
}
