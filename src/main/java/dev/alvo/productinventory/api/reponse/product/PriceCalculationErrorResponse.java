package dev.alvo.productinventory.api.reponse.product;

import org.springframework.http.ResponseEntity;

public class PriceCalculationErrorResponse extends ProductResponse {

  public PriceCalculationErrorResponse(String currency) {
    super("Was not able to calculate price for currency: " + currency);
  }

  @Override
  public ResponseEntity<ProductResponse> asResponseEntity() {
    return ResponseEntity.badRequest().body(this);
  }
}
