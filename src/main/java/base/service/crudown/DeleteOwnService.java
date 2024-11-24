package base.service.crudown;

import base.abstractions.Identifiable;
import base.service.DeleteService;
import base.service.ReadService;
import base.service.abstractions.BaseOwnService;
import java.util.Objects;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;


/**
 * Сервис, способный удалять сущности, если пользователь имеет доступ
 *
 * @param <D> Тип дто
 * @param <E> Тип Сущности
 * @param <I> Тип идентификатора сущности
 */
public interface DeleteOwnService<D, E extends Identifiable<E>, I> extends BaseOwnService<D, E> {

  default DeleteService<D, E, I> svcDelete() {
    return (DeleteService<D, E, I>) svc();
  }

  default ReadService<D, E, I> svcRead() {
    return (ReadService<D, E, I>) svc();
  }

  /**
   * Возвращает спецификацию для проверки прав текущего пользователя на удаление сущности.
   *
   * @param currentUserId ID текущего пользователя.
   * @return спецификация для проверки прав.
   */
  Specification<E> deleteFilterByUserId(Long currentUserId);

  // region Delete Methods

  @Transactional
  default void deleteById(I id, Long currentUserId) {
    Specification<E> spec = deleteFilterByUserId(currentUserId)
        .and((root, query, cb) -> cb.equal(root.get("id"), id));
    svcRead().findOne(spec).ifPresentOrElse(
        svcDelete()::delete,
        () -> {
          throw new SecurityException("Access denied or entity not found for ID: " + id);
        }
    );
  }

  @Transactional
  default void delete(E entity, Long currentUserId) {
    Objects.requireNonNull(entity, "Entity cannot be null");
    Specification<E> spec = deleteFilterByUserId(currentUserId)
        .and((root, query, cb) -> cb.equal(root.get("id"), entity.getId()));
    svcRead().findOne(spec).ifPresentOrElse(
        svcDelete()::delete,
        () -> {
          throw new SecurityException("Access denied or entity not found: " + entity);
        }
    );
  }

  @Transactional
  default void deleteAllById(Iterable<I> ids, Long currentUserId) {
    Specification<E> spec = deleteFilterByUserId(currentUserId)
        .and((root, query, cb) -> root.get("id").in(ids));
    svcRead().findAll(spec).forEach(svcDelete()::delete);
  }

  @Transactional
  default void deleteAll(Iterable<E> entities, Long currentUserId) {
    entities.forEach(entity -> delete(entity, currentUserId));
  }

  // endregion
}
