package com.example.letsplay.user.dto;


import jakarta.validation.constraints.Size;


public class UpdateUserRequest {
@Size(min=2, max=50)
private String name;


public String getName() { return name; }
public void setName(String name) { this.name = name; }
}
