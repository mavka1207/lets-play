package com.example.letsplay.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CreateProductRequest {

  @NotBlank(message = "name must not be blank")
  @Size(min = 2, max = 100, message = "name size must be between 2 and 100")
  private String name;

  @Size(max = 500, message = "description size must be <= 500")
  private String description;

  @Positive(message = "price must be positive")
  private Double price;

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }

  public Double getPrice() { return price; }
  public void setPrice(Double price) { this.price = price; }
}
