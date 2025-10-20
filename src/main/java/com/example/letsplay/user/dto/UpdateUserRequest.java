package com.example.letsplay.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import com.example.letsplay.user.Role;

/** Admin-side user update (all fields optional). */
public class UpdateUserRequest {
  @Size(min = 2, max = 100)
  private String name;

  @Email
  private String email;

  @Size(min = 8, max = 200)
  private String password;

  private Role role; // optional; enforce RBAC in service/controller

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }

  public String getPassword() { return password; }
  public void setPassword(String password) { this.password = password; }

  public Role getRole() { return role; }
  public void setRole(Role role) { this.role = role; }
}
