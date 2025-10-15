package com.example.letsplay.product.dto;


import jakarta.validation.constraints.*;


public class CreateProductRequest {
@NotBlank @Size(min=2,max=100)
private String name;
@NotBlank @Size(min=5,max=500)
private String description;
@NotNull @Positive
private Double price;


public String getName() { return name; }
public void setName(String name) { this.name = name; }
public String getDescription() { return description; }
public void setDescription(String description) { this.description = description; }
public Double getPrice() { return price; }
public void setPrice(Double price) { this.price = price; }
}
