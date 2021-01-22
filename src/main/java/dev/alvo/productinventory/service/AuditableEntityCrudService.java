package dev.alvo.productinventory.service;

import dev.alvo.productinventory.conversion.EntityBiConverter;
import dev.alvo.productinventory.persistence.entity.AbstractAuditableEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

/**
 * Abstract CRUD functionality boilerplate for the base {@link AbstractAuditableEntity}
 *
 * @param <Entity>     Target persistable entity
 * @param <Repository> Repository used to perform persistence CRUD operations
 * @param <Model>      Application level entity representation
 * @param <Converter>  Isomorphism between Model and Entity
 */
public abstract class AuditableEntityCrudService<
  Entity extends AbstractAuditableEntity,
  Repository extends PagingAndSortingRepository<Entity, Long>,
  Model,
  Converter extends EntityBiConverter<Entity, Model>> {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuditableEntityCrudService.class);

  protected final Converter converter;
  protected final Repository repository;

  protected AuditableEntityCrudService(Converter converter, Repository repository) {
    this.converter = converter;
    this.repository = repository;
  }

  public Optional<Model> create(Model model) {
    if (!exists(model)) {
      return converter.convertToEntity(model)
        .map(repository::save)
        .flatMap(converter::convertFromEntity);
    } else {
      LOGGER.error("Model: {} can't be saved, such entity already exists!", model);
      return Optional.empty();
    }
  }

  public Page<Model> getAll(Pageable pageable) {
    final var total = repository.count();
    final var models = repository.findAll(pageable)
      .flatMap(model -> converter.convertFromEntity(model).stream())
      .toList();

    return new PageImpl<>(models, pageable, total);
  }

  public Optional<Model> get(Long id) {
    return repository.findById(id).flatMap(converter::convertFromEntity);
  }

  public Optional<Model> update(Long id, Model newModel) {
    if (!exists(newModel)) {
      return repository.findById(id)
        .flatMap(existingEntity -> mapModelToExistingEntity(newModel, existingEntity))
        .map(repository::save)
        .flatMap(converter::convertFromEntity);
    } else {
      LOGGER.error("Entity can't be updated, such entity already exists: {}", newModel);
      return Optional.empty();
    }
  }

  public Optional<Model> markForDeletion(Long id) {
    return repository.findById(id)
      .map(this::markDeleted)
      .map(repository::save)
      .flatMap(converter::convertFromEntity);
  }

  public Optional<Model> delete(Long id) {
    return repository.findById(id)
      .filter(Entity::getDeleted)
      .flatMap(markedForDeletionCategory -> {
        repository.delete(markedForDeletionCategory);
        return converter.convertFromEntity(markedForDeletionCategory);
      });
  }

  public void unsafeDelete(Long id) {
    LOGGER.warn("UNSAFE category deletion execution!");
    repository.deleteById(id);
  }

  private Entity markDeleted(Entity entity) {
    entity.setDeleted(true);
    return entity;
  }

  protected abstract Optional<Entity> mapModelToExistingEntity(Model model, Entity entity);

  protected abstract boolean exists(Model model);
}
