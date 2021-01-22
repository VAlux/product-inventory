package dev.alvo.productinventory.api.request.category;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@ApiModel(description = "Update category request")
public final class CategoryUpdateRequest {

  @PositiveOrZero
  private final Long id;

  @NotBlank
  private final String name;

  @JsonCreator
  public CategoryUpdateRequest(@JsonProperty("id") Long id, @JsonProperty("name") String name) {
    this.id = id;
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
