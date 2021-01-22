package dev.alvo.productinventory.api.reponse.product;

import org.springframework.http.ResponseEntity;

public final class ErrorCreatingProductResponse extends ProductResponse {
  public ErrorCreatingProductResponse() {
    super("Error creating product! Probably product with such name already exists or category is invalid");
  }

  @Override
  public ResponseEntity<ProductResponse> asResponseEntity() {
    return ResponseEntity.badRequest().body(this);
  }
}
