package dev.alvo.productinventory.conversion;

import dev.alvo.productinventory.model.Category;
import dev.alvo.productinventory.persistence.entity.CategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CategoryEntityConverter implements EntityBiConverter<CategoryEntity, Category> {

  private final ProductEntityConverter productEntityConverter;

  @Autowired
  public CategoryEntityConverter(ProductEntityConverter productEntityConverter) {
    this.productEntityConverter = productEntityConverter;
  }

  @Override
  public Optional<CategoryEntity> convertToEntity(Category category) {
    var products = productEntityConverter.convertToEntity(category.getProducts());
    return Optional.of(new CategoryEntity(category.getName(), products));
  }

  @Override
  public Optional<Category> convertFromEntity(CategoryEntity entity) {
    var products = productEntityConverter.convertFromEntity(entity.getProducts());
    return Optional.of(new Category(entity.getName(), products));
  }
}
