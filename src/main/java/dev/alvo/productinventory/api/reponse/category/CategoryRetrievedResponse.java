package dev.alvo.productinventory.api.reponse.category;

import dev.alvo.productinventory.model.Product;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Set;

public final class CategoryRetrievedResponse extends CategoryResponse {

  private final String name;
  private final Set<Product> products;

  public CategoryRetrievedResponse(String name, Set<Product> products) {
    super("Category with name: '" + name + "' retrieved");
    this.name = name;
    this.products = products;
  }

  public String getName() {
    return name;
  }

  public Set<Product> getProducts() {
    return new HashSet<>(products);
  }

  @Override
  public ResponseEntity<CategoryResponse> asResponseEntity() {
    return ResponseEntity.ok(this);
  }
}
