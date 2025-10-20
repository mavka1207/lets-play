package com.example.letsplay.product;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/** DTO for updating a product (all fields optional). */
public class UpdateProductRequest {

  @Size(min = 2, max = 100)
  private String name;

  @Size(max = 500)
  private String description;

  @Positive
  private Double price;

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }

  public Double getPrice() { return price; }
  public void setPrice(Double price) { this.price = price; }
}
