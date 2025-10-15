package com.example.letsplay.product;


import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "products")
public class Product {
@Id
private String id;


@NotBlank
@Size(min = 2, max = 100)
private String name;


@NotBlank
@Size(min = 5, max = 500)
private String description;


@NotNull
@Positive
private Double price;


@NotBlank
private String userId; // owner


public String getId() { return id; }
public void setId(String id) { this.id = id; }
public String getName() { return name; }
public void setName(String name) { this.name = name; }
public String getDescription() { return description; }
public void setDescription(String description) { this.description = description; }
public Double getPrice() { return price; }
public void setPrice(Double price) { this.price = price; }
public String getUserId() { return userId; }
public void setUserId(String userId) { this.userId = userId; }
}
