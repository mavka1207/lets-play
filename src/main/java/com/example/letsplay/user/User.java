package com.example.letsplay.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * User entity stored in MongoDB and used by Spring Security.
 * Password is write-only in JSON to avoid leaking it in responses.
 */
@Document(collection = "users")
public class User implements UserDetails {

  @Id
  private String id;

  @NotNull
  @NotBlank
  @Size(min = 2, max = 100)
  @Field("name")
  private String name;

  @NotNull
  @NotBlank
  @Email
  @Indexed(unique = true)
  @Field("email")
  private String email;

  @NotNull
  @NotBlank
  @Size(min = 8, max = 200)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @Field("password")
  private String password;

  @Field("role")
  private Role role = Role.USER;

  public String getId() { return id; }
  public void setId(String id) { this.id = id; }

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }

  public String getPassword() { return password; }
  public void setPassword(String password) { this.password = password; }

  public Role getRole() { return role; }
  public void setRole(Role role) { this.role = role; }

  // --- UserDetails ---

  @Override
  @JsonIgnore
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // Spring Security expects ROLE_ prefix when using hasRole('ADMIN')
    return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
  }

  @Override public String getUsername() { return email; }
  @Override @JsonIgnore public boolean isAccountNonExpired() { return true; }
  @Override @JsonIgnore public boolean isAccountNonLocked() { return true; }
  @Override @JsonIgnore public boolean isCredentialsNonExpired() { return true; }
  @Override @JsonIgnore public boolean isEnabled() { return true; }
}
