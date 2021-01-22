package dev.alvo.productinventory.api.request.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@ApiModel(description = "Existing product updating request")
public final class ProductUpdateRequest {

  @PositiveOrZero
  @ApiModelProperty(example = "42", required = true)
  private final Long id;

  @NotBlank
  @ApiModelProperty(example = "fancy product", required = true)
  private final String name;

  @PositiveOrZero
  @ApiModelProperty(example = "500.21", required = true)
  private final BigDecimal price;

  @ApiModelProperty(example = "USD")
  private final String currency;

  @NotNull
  @ApiModelProperty(example = "42", required = true)
  private final Long categoryId;

  public ProductUpdateRequest(@JsonProperty("id") Long id,
                              @JsonProperty("name") String name,
                              @JsonProperty("price") BigDecimal price,
                              @JsonProperty("categoryId") Long categoryId,
                              @JsonProperty("currency") String currency) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.currency = currency;
    this.categoryId = categoryId;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public Long getCategoryId() {
    return categoryId;
  }

  public String getCurrency() {
    return currency;
  }
}
