package base.service.abstractions;

import base.transformer.Transformer;
import java.util.Collection;


/**
 * Базовая абстракция для любого сервиса
 *
 * @param <D> Тип дто
 * @param <E> Тип Сущности
 * @author Ivan Zhendorenko
 */
public interface BaseService<D, E> {


  /**
   * Трансформер дто <-> бд-сущность
   *
   * @return Трансформер
   */
  Transformer<D, E> t();

  /**
   * Обогатить dto какими-либо данными
   *
   * @param dto dto
   * @return Обогащённая dto
   */
  default D enrichDto(D dto) {
    return dto;
  }

  default E enrichEntity(E entity) {
    return entity;
  }

  default Collection<D> enrichDtos(Collection<D> dtos) {
    return dtos;
  }

  default Collection<E> enrichEntitys(Collection<E> entitys) {
    return entitys;
  }

}