package dev.alvo.productinventory.api.reponse.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ProductNotFoundResponse extends ProductResponse {

  public ProductNotFoundResponse(Long id) {
    super("Product cannot be found for id: " + id);
  }

  @Override
  public ResponseEntity<ProductResponse> asResponseEntity() {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(this);
  }
}
