package base.service;

import base.service.abstractions.BaseJpaService;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import org.springframework.transaction.annotation.Transactional;


/**
 * Сервис, способный изменять сущности
 *
 * @param <D> Тип дто
 * @param <E> Тип Сущности
 * @param <I> Тип идентификатора сущности
 * @author Ivan Zhendorenko
 */
public interface UpdateService<D, E, I> extends BaseJpaService<D, E, I> {

  //region entity

  @Transactional
  default E update(E entity, Consumer<E> mutator) {
    mutator.accept(entity);
    return repo().save(entity);
  }

  @Transactional
  default E updateAndFlush(E entity, Consumer<E> mutator) {
    mutator.accept(entity);
    return repo().saveAndFlush(entity);
  }

  @Transactional
  default List<E> updateAll(Iterable<E> entities, Consumer<E> mutator) {
    entities.forEach(mutator);
    return repo().saveAll(entities);
  }

  @Transactional
  default List<E> updateAllAndFlush(Iterable<E> entities, Consumer<E> mutator) {
    entities.forEach(mutator);
    return repo().saveAllAndFlush(entities);
  }

  //endregion

  //region dto

  @Transactional
  default D updateDto(D entity, Consumer<E> mutator) {
    return enrichDto(t().entityToDto(update(enrichEntity(t().dtoToEntity(entity)), mutator)));
  }

  @Transactional
  default D updateAndFlushDto(D entity, Consumer<E> mutator) {
    return enrichDto(t().entityToDto(updateAndFlush(enrichEntity(t().dtoToEntity(entity)), mutator)));
  }

  @Transactional
  default List<D> updateAllDto(Collection<D> entities, Consumer<E> mutator) {
    return t().entitiesToDtos(updateAll(enrichEntitys(t().dtosToEntities(entities)), mutator)).stream().map(this::enrichDto).toList();
  }

  @Transactional
  default List<D> updateAllAndFlushDto(Collection<D> entities, Consumer<E> mutator) {
    return t().entitiesToDtos(updateAllAndFlush(enrichEntitys(t().dtosToEntities(entities)), mutator)).stream().map(this::enrichDto).toList();
  }

  //endregion
}
