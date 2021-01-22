package dev.alvo.productinventory.api.reponse.category;

import org.springframework.http.ResponseEntity;

public final class ErrorCreatingCategoryResponse extends CategoryResponse {
  public ErrorCreatingCategoryResponse() {
    super("Error creating/updating category! Probably category with specified name already exists.");
  }

  @Override
  public ResponseEntity<CategoryResponse> asResponseEntity() {
    return ResponseEntity.badRequest().body(this);
  }
}
