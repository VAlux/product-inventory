package dev.alvo.productinventory.service;

import dev.alvo.productinventory.conversion.ProductEntityConverter;
import dev.alvo.productinventory.model.Product;
import dev.alvo.productinventory.persistence.entity.CategoryEntity;
import dev.alvo.productinventory.persistence.entity.ProductEntity;
import dev.alvo.productinventory.persistence.repository.CategoryRepository;
import dev.alvo.productinventory.persistence.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@MockitoSettings(strictness = Strictness.LENIENT)
class ProductServiceTest {


  @Mock
  private ProductRepository productRepository;

  @Mock
  private CategoryRepository categoryRepository;

  private ProductService productService;

  @BeforeEach
  void setUp() {
    var productConverter = new ProductEntityConverter(categoryRepository);
    this.productService = new ProductService(productConverter, productRepository, categoryRepository);
  }

  @Test
  void testCreate() {
    var price = BigDecimal.valueOf(42.24);
    var name = "test";
    var categoryId = 1L;

    var model = new Product(name, price, categoryId);
    var categoryEntity = new CategoryEntity(categoryId, "test categoryEntity", Set.of());
    var entity = new ProductEntity(name, price, categoryEntity);
    categoryEntity.setProducts(Set.of(entity));

    Mockito.when(categoryRepository.findById(categoryId))
      .thenReturn(Optional.of(categoryEntity));

    Mockito.when(productRepository.save(ArgumentMatchers.any()))
      .thenReturn(new ProductEntity(name, price, categoryEntity));

    var result = this.productService.create(model);

    Assertions.assertTrue(result.isPresent());
  }

  @Test
  void testRead() {
    var price = BigDecimal.valueOf(42.24);
    var name = "test";
    var categoryId = 1L;

    var categoryEntity = new CategoryEntity(categoryId, "test categoryEntity", Set.of());
    var entity = new ProductEntity(name, price, categoryEntity);
    categoryEntity.setProducts(Set.of(entity));

    Mockito.when(productRepository.findById(1L))
      .thenReturn(Optional.of(entity));

    var result = this.productService.get(1L);

    Assertions.assertTrue(result.isPresent());
  }


  @Test
  void testUpdate() {
    var price = BigDecimal.valueOf(42.24);
    var name = "test";
    var categoryId = 1L;

    var updatedPrice = price.add(BigDecimal.ONE);
    var model = new Product(name, updatedPrice, categoryId);
    var categoryEntity = new CategoryEntity(categoryId, "test categoryEntity", Set.of());
    var entity = new ProductEntity(name, price, categoryEntity);
    var updatedEntity = new ProductEntity(name, updatedPrice, categoryEntity);
    categoryEntity.setProducts(Set.of(entity));

    Mockito.when(productRepository.findById(1L))
      .thenReturn(Optional.of(entity));

    Mockito.when(categoryRepository.findById(categoryId))
      .thenReturn(Optional.of(categoryEntity));

    Mockito.when(productRepository.save(ArgumentMatchers.any()))
      .thenReturn(updatedEntity);

    var result = this.productService.update(1L, model);

    Assertions.assertTrue(result.isPresent());
    Assertions.assertEquals(updatedPrice, result.get().getPrice());
  }

  @Test
  void testDelete() {
    var price = BigDecimal.valueOf(42.24);
    var name = "test";
    var categoryId = 1L;

    var categoryEntity = new CategoryEntity(categoryId, "test categoryEntity", Set.of());
    var entity = new ProductEntity(name, price, categoryEntity);
    entity.setDeleted(true);
    categoryEntity.setProducts(Set.of(entity));

    Mockito.when(productRepository.findById(1L))
      .thenReturn(Optional.of(entity));

    var result = this.productService.delete(1L);

    Assertions.assertTrue(result.isPresent());
  }

  @Test
  void testDeleteSafe() {
    var price = BigDecimal.valueOf(42.24);
    var name = "test";
    var categoryId = 1L;

    var categoryEntity = new CategoryEntity(categoryId, "test categoryEntity", Set.of());
    var entity = new ProductEntity(name, price, categoryEntity);
    entity.setDeleted(false);
    categoryEntity.setProducts(Set.of(entity));

    Mockito.when(productRepository.findById(1L))
      .thenReturn(Optional.of(entity));

    var result = this.productService.delete(1L);

    Assertions.assertFalse(result.isPresent());
  }
}