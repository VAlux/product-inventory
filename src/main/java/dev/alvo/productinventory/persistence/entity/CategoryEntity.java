package dev.alvo.productinventory.persistence.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "categories")
public class CategoryEntity extends AbstractAuditableEntity {

  private String name;

  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
  private Set<ProductEntity> products;

  public CategoryEntity() {
  }

  public CategoryEntity(String name) {
    this.name = name;
  }

  public CategoryEntity(String name, Set<ProductEntity> products) {
    this.name = name;
    this.products = products;
  }

  public CategoryEntity(Long id, String name, Set<ProductEntity> products) {
    this(name, products);
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setProducts(Set<ProductEntity> products) {
    this.products = products;
  }

  public Set<ProductEntity> getProducts() {
    return products;
  }
}
