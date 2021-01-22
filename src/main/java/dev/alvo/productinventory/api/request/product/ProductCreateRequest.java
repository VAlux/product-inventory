package dev.alvo.productinventory.api.request.product;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@ApiModel(description = "New product creation request")
public final class ProductCreateRequest {

  @NotBlank
  @ApiModelProperty(example = "fancy product", required = true)
  private final String name;

  @PositiveOrZero
  @ApiModelProperty(example = "500.23", required = true)
  private final BigDecimal price;

  @NotNull
  @ApiModelProperty(example = "42", required = true)
  private final Long categoryId;

  @ApiModelProperty(example = "USD")
  private final String currency;

  public ProductCreateRequest(String name, BigDecimal price, Long categoryId) {
    this.name = name;
    this.price = price;
    this.categoryId = categoryId;
    this.currency = "EUR";
  }

  @JsonCreator
  public ProductCreateRequest(@JsonProperty("name") String name,
                              @JsonProperty("price") BigDecimal price,
                              @JsonProperty("categoryId") Long categoryId,
                              @JsonProperty("currency") String currency) {
    this.name = name;
    this.price = price;
    this.categoryId = categoryId;
    this.currency = currency;
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
