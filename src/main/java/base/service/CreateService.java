package base.service;

import base.service.abstractions.BaseJpaService;
import java.util.Collection;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;


/**
 * Сервис, способный создавать сущности
 *
 * @param <D> Тип дто
 * @param <E> Тип Сущности
 * @param <I> Тип идентификатора сущности
 * @author Ivan Zhendorenko
 */
public interface CreateService<D, E, I> extends BaseJpaService<D, E, I> {

  // region entity

  @Transactional
  default E save(E entity) {
    return repo().save(entity);
  }

  @Transactional
  default E saveAndFlush(E entity) {
    return repo().saveAndFlush(entity);
  }

  @Transactional
  default List<E> saveAll(Iterable<E> entities) {
    return repo().saveAll(entities);
  }

  @Transactional
  default List<E> saveAllAndFlush(Iterable<E> entities) {
    return repo().saveAllAndFlush(entities);
  }

  // endregion

  // region dto

  @Transactional
  default D saveDto(D d) {
    return enrichDto(t().entityToDto(save(enrichEntity(t().dtoToEntity(d)))));
  }

  @Transactional
  default D saveAndFlushDto(D d) {
    return enrichDto(t().entityToDto(saveAndFlush(enrichEntity(t().dtoToEntity(d)))));
  }

  @Transactional
  default List<D> saveAllDto(Collection<D> ds) {
    return t().entitiesToDtos(saveAll(enrichEntitys(t().dtosToEntities(ds)))).stream().map(this::enrichDto).toList();
  }

  @Transactional
  default List<D> saveAllAndFlushDto(Collection<D> ds) {
    return t().entitiesToDtos(saveAllAndFlush(enrichEntitys(t().dtosToEntities(ds)))).stream().map(this::enrichDto).toList();
  }

  // endregion
}
