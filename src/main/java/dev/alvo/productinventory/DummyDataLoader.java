package dev.alvo.productinventory;

import dev.alvo.productinventory.persistence.entity.CategoryEntity;
import dev.alvo.productinventory.persistence.entity.ProductEntity;
import dev.alvo.productinventory.persistence.repository.CategoryRepository;
import dev.alvo.productinventory.persistence.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;

@Component
public class DummyDataLoader {

  private static final Logger LOGGER = LoggerFactory.getLogger(DummyDataLoader.class);

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;

  @Autowired
  public DummyDataLoader(ProductRepository productRepository, CategoryRepository categoryRepository) {
    this.productRepository = productRepository;
    this.categoryRepository = categoryRepository;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void insertDummyData() {
    LOGGER.info("Loading dummy data into the database...");

    var category = new CategoryEntity("category_one");

    var product1 = new ProductEntity("product1", BigDecimal.valueOf(400), category);
    var product2 = new ProductEntity("product2", BigDecimal.valueOf(500), category);
    var product3 = new ProductEntity("product3", BigDecimal.valueOf(10), category);
    var product4 = new ProductEntity("product4", BigDecimal.valueOf(5000), category);

    var products = Set.of(product1, product2, product3, product4);

    categoryRepository.save(category);
    productRepository.saveAll(products);
  }
}
