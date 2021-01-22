package dev.alvo.productinventory.api.reponse.product;

import org.springframework.http.ResponseEntity;

public abstract class ProductResponse {

	/**
	 * Intended to be used to tracing/logging functionality on client side
   */
  private final String message;

  protected ProductResponse(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public abstract ResponseEntity<ProductResponse> asResponseEntity();
}
