package dev.alvo.productinventory.api.reponse.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

@ApiModel(value = "Product retrieved response")
public final class ProductRetrievedResponse extends ProductResponse {

  @ApiModelProperty(value = "Name of the product")
  private final String name;

  @ApiModelProperty(value = "Price of the product in EUR")
  private final BigDecimal price;

  @ApiModelProperty(value = "Category id of the product")
  private final Long categoryId;

  public ProductRetrievedResponse(String name, BigDecimal price, Long categoryId) {
    super("Product with name '" + name + "' retrieved");
    this.name = name;
    this.price = price;
    this.categoryId = categoryId;
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

  @Override
  public ResponseEntity<ProductResponse> asResponseEntity() {
    return ResponseEntity.ok(this);
  }
}
