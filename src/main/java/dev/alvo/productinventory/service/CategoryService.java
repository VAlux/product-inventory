package dev.alvo.productinventory.service;

import dev.alvo.productinventory.conversion.CategoryEntityConverter;
import dev.alvo.productinventory.model.Category;
import dev.alvo.productinventory.persistence.entity.CategoryEntity;
import dev.alvo.productinventory.persistence.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService
  extends AuditableEntityCrudService<CategoryEntity, CategoryRepository, Category, CategoryEntityConverter> {

  @Autowired
  public CategoryService(CategoryEntityConverter converter, CategoryRepository repository) {
    super(converter, repository);
  }

  @Override
  protected Optional<CategoryEntity> mapModelToExistingEntity(Category category, CategoryEntity existing) {
    existing.setName(category.getName());
    return Optional.of(existing);
  }

  @Override
  protected boolean exists(Category category) {
    return this.repository.existsByName(category.getName());
  }
}
