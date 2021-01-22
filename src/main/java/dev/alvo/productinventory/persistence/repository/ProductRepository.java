package dev.alvo.productinventory.persistence.repository;

import dev.alvo.productinventory.persistence.entity.ProductEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<ProductEntity, Long> {
  Boolean existsByName(String name);
}
