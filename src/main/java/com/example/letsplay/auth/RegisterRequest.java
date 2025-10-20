package com.example.letsplay.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** Request body for POST /auth/register. */
public class RegisterRequest {
  @NotBlank @Size(min = 2, max = 100) private String name;
  @NotBlank @Email private String email;
  @NotBlank @Size(min = 8, max = 200) private String password;

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  public String getPassword() { return password; }
  public void setPassword(String password) { this.password = password; }
}
