package dev.alvo.productinventory.persistence.repository;

import dev.alvo.productinventory.persistence.entity.CategoryEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<CategoryEntity, Long> {
  Boolean existsByName(String name);
}
