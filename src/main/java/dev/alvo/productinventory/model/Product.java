package dev.alvo.productinventory.model;

import java.math.BigDecimal;

public class Product {

  private final String name;
  private final BigDecimal price;
  private final Long categoryId;

  public Product(String name, BigDecimal price, Long categoryId) {
    this.name = name;
    this.categoryId = categoryId;
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public Long getCategoryId() {
    return categoryId;
  }

  public BigDecimal getPrice() {
    return price;
  }

  @Override
  public String toString() {
    return "Product{" +
      "name='" + name + '\'' +
      ", price=" + price +
      ", categoryId=" + categoryId +
      '}';
  }
}
