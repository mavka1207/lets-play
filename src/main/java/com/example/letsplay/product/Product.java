package com.example.letsplay.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** Product entity. Owned by a User (userId). */
@Document(collection = "products")
public class Product {

  @Id
  private String id;

  
  @NotBlank
  @Size(min = 2, max = 100)
  @Field("name")
  private String name;

  @Size(max = 500)
  @Field("description")
  private String description;

  @Positive
  @Field("price")
  private Double price;

  /** Owner user id used for RBAC checks. */
  @NotBlank
  @Field("userId")
  private String userId;

  public Product() {}

  public Product(String id, String name, String description, Double price, String userId) {
    this.id = id; this.name = name; this.description = description; this.price = price; this.userId = userId;
  }

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
