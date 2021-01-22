package dev.alvo.productinventory.api.reponse.category;

import org.springframework.http.ResponseEntity;

public abstract class CategoryResponse {

  /**
   * Intended to be used to tracing/logging functionality on client side
   */
  private final String message;

  protected CategoryResponse(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public abstract ResponseEntity<CategoryResponse> asResponseEntity();
}
