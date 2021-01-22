package dev.alvo.productinventory.conversion;

import dev.alvo.productinventory.model.Product;
import dev.alvo.productinventory.persistence.entity.ProductEntity;
import dev.alvo.productinventory.persistence.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProductEntityConverter implements EntityBiConverter<ProductEntity, Product> {

  private final CategoryRepository categoryRepository;

  @Autowired
  public ProductEntityConverter(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Override
  public Optional<ProductEntity> convertToEntity(Product product) {
    return categoryRepository.findById(product.getCategoryId())
      .map(category -> new ProductEntity(product.getName(), product.getPrice(), category));
  }

  @Override
  public Optional<Product> convertFromEntity(ProductEntity productEntity) {
    var product = new Product(productEntity.getName(), productEntity.getPrice(), productEntity.getCategory().getId());
    return Optional.of(product);
  }

  public Set<Product> convertFromEntity(Set<ProductEntity> products) {
    return products.stream()
      .flatMap(entity -> this.convertFromEntity(entity).stream())
      .collect(Collectors.toSet());
  }

  public Set<ProductEntity> convertToEntity(Set<Product> products) {
    return products.stream()
      .flatMap(model -> this.convertToEntity(model).stream())
      .collect(Collectors.toSet());
  }
}
