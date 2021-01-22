package dev.alvo.productinventory.model;

import java.util.Set;

public class Category {

  private final String name;
  private final Set<Product> products;

  public Category(String name, Set<Product> products) {
    this.name = name;
    this.products = products;
  }

  public String getName() {
    return name;
  }

  public Set<Product> getProducts() {
    return products;
  }

  @Override
  public String toString() {
    return "Category{" +
      "name='" + name + '\'' +
      ", products=" + products +
      '}';
  }
}
