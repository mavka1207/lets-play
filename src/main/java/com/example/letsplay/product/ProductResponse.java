package com.example.letsplay.product;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse {

  private String id;
  private String name;
  private String description;
  private Double price;
  // private String userId;

  public static ProductResponse from(Product p) {
    ProductResponse r = new ProductResponse();
    if (p == null) return r;
    r.setId(p.getId());
    r.setName(p.getName());
    r.setDescription(p.getDescription());
    r.setPrice(p.getPrice());
    // r.setUserId(p.getUserId());
    return r;
  }

  public String getId() { return id; }
  public void setId(String id) { this.id = id; }

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }

  public Double getPrice() { return price; }
  public void setPrice(Double price) { this.price = price; }

  // public String getUserId() { return userId; }
  // public void setUserId(String userId) { this.userId = userId; }
}
