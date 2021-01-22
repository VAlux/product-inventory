package dev.alvo.productinventory.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class ProductEntity extends AbstractAuditableEntity {

  private String name;

  private BigDecimal price;

  @ManyToOne(optional = false)
  @JoinColumn(name = "category_id", nullable = false)
  private CategoryEntity category;

  public ProductEntity() {
  }

  public ProductEntity(String name, BigDecimal price, CategoryEntity category) {
    this.name = name;
    this.price = price;
    this.category = category;
  }

  public ProductEntity(Long id, String name, BigDecimal price, CategoryEntity category) {
    this(name, price, category);
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public CategoryEntity getCategory() {
    return category;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public void setCategory(CategoryEntity category) {
    this.category = category;
  }
}
