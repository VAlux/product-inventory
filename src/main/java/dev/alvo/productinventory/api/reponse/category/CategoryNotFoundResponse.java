package dev.alvo.productinventory.api.reponse.category;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class CategoryNotFoundResponse extends CategoryResponse {

  public CategoryNotFoundResponse(Long id) {
    super("Category cannot be deleted for id: " + id + ". Probably it is not marked for deletion or does not exist.");
  }

  @Override
  public ResponseEntity<CategoryResponse> asResponseEntity() {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(this);
  }
}
