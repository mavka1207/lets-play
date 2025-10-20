package com.example.letsplay.user.dto;

import com.example.letsplay.user.User;
import com.example.letsplay.user.Role;

/** Read-only DTO for returning user info (never exposes the password). */
public class UserResponse {
  private String id;
  private String name;
  private String email;
  private Role role;

  public static UserResponse from(User u) {
    UserResponse r = new UserResponse();
    r.id = u.getId();
    r.name = u.getName();
    r.email = u.getEmail();
    r.role = u.getRole();
    return r;
  }

  public String getId() { return id; }
  public String getName() { return name; }
  public String getEmail() { return email; }
  public Role getRole() { return role; }
}
