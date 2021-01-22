package dev.alvo.productinventory.service;

import dev.alvo.productinventory.conversion.ProductEntityConverter;
import dev.alvo.productinventory.model.Product;
import dev.alvo.productinventory.persistence.entity.ProductEntity;
import dev.alvo.productinventory.persistence.repository.CategoryRepository;
import dev.alvo.productinventory.persistence.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService
  extends AuditableEntityCrudService<ProductEntity, ProductRepository, Product, ProductEntityConverter> {

  private final CategoryRepository categoryRepository;

  @Autowired
  public ProductService(ProductEntityConverter converter,
                        ProductRepository repository,
                        CategoryRepository categoryRepository) {

    super(converter, repository);
    this.categoryRepository = categoryRepository;
  }

  @Override
  protected Optional<ProductEntity> mapModelToExistingEntity(Product product, ProductEntity entity) {
    return categoryRepository.findById(product.getCategoryId())
      .map(categoryEntity -> new ProductEntity(entity.getId(), product.getName(), product.getPrice(), categoryEntity));
  }

  @Override
  protected boolean exists(Product product) {
    return repository.existsByName(product.getName());
  }
}
