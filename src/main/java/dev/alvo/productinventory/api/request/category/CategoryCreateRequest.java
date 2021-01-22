package dev.alvo.productinventory.api.request.category;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotBlank;

@ApiModel(description = "Create category request")
public final class CategoryCreateRequest {

  @NotBlank
  private final String name;

  @JsonCreator
  public CategoryCreateRequest(@JsonProperty("name") String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
