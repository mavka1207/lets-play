package com.example.letsplay.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;


@Document(collection = "users")
public class User implements UserDetails {


@Id
private String id;


@NotBlank
@Size(min = 2, max = 50)
private String name;


@Email
@NotBlank
@Indexed(unique = true)
private String email;


@NotBlank
@Size(min = 8, max = 100)
private String password;


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
@Override public Collection<? extends GrantedAuthority> getAuthorities() {
return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
}
@Override public String getUsername() { return email; }
@Override public boolean isAccountNonExpired() { return true; }
@Override public boolean isAccountNonLocked() { return true; }
@Override public boolean isCredentialsNonExpired() { return true; }
@Override public boolean isEnabled() { return true; }
}
