package dev.alvo.productinventory.conversion;

import dev.alvo.productinventory.persistence.entity.AbstractAuditableEntity;

import java.util.Optional;

/**
 * Basic isomorphism between 2 types
 *
 * @param <A> entity type
 * @param <B> model type
 */
public interface EntityBiConverter<A extends AbstractAuditableEntity, B> {
  Optional<A> convertToEntity(final B input);

  Optional<B> convertFromEntity(final A input);
}
