package com.example.letsplay.product.dto;


public class ProductResponse {
private String id;
private String name;
private String description;
private Double price;
private String userId;


public ProductResponse() {}
public ProductResponse(String id, String name, String description, Double price, String userId) {
this.id=id; this.name=name; this.description=description; this.price=price; this.userId=userId;
}


public String getId() { return id; }
public String getName() { return name; }
public String getDescription() { return description; }
public Double getPrice() { return price; }
public String getUserId() { return userId; }
}
