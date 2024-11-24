package base.service.crudown;

import base.abstractions.IdentifiableDto;
import base.service.ReadService;
import base.service.UpdateService;
import base.service.abstractions.BaseOwnService;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import lombok.experimental.ExtensionMethod;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;


/**
 * Сервис, способный изменять сущности, если пользователь имеет доступ.
 *
 * @param <D> Тип дто
 * @param <E> Тип Сущности
 * @param <I> Тип идентификатора сущности
 */
@ExtensionMethod(base.util.Data.class)
public interface UpdateOwnService<D extends IdentifiableDto<D>, E, I> extends BaseOwnService<D, E> {

  default UpdateService<D, E, I> svcUpdate() {
    return (UpdateService<D, E, I>) svc();
  }

  default ReadService<D, E, I> svcRead() {
    return (ReadService<D, E, I>) svc();
  }

  /**
   * Возвращает спецификацию для проверки прав текущего пользователя на изменение сущности.
   *
   * @param currentUserId ID текущего пользователя.
   * @return спецификация для проверки прав.
   */
  Specification<E> updateFilterByUserId(Long currentUserId);

  // region DTO

  @Transactional
  default D updateDto(D dto, Consumer<E> mutator, Long currentUserId) {
    Objects.requireNonNull(dto, "DTO cannot be null");
    Specification<E> spec = updateFilterByUserId(currentUserId)
        .and((root, query, cb) -> cb.equal(root.get("id"), dto.getId()));
    return svcRead().t().entityToDto(svcRead().findOne(spec)
        .map(entity -> {
          mutator.accept(entity);
          return svcUpdate().update(entity, e -> {
          });
        })
        .orElseThrow(() -> new SecurityException("Access denied or entity not found for update.")));
  }

  @Transactional
  default D updateAndFlushDto(D dto, Consumer<E> mutator, Long currentUserId) {
    Objects.requireNonNull(dto, "DTO cannot be null");
    Specification<E> spec = updateFilterByUserId(currentUserId)
        .and((root, query, cb) -> cb.equal(root.get("id"), dto.getId()));
    return svcRead().t().entityToDto(svcRead().findOne(spec)
        .map(entity -> {
          mutator.accept(entity);
          return svcUpdate().updateAndFlush(entity, e -> {
          });
        })
        .orElseThrow(() -> new SecurityException("Access denied or entity not found for update.")));
  }

  @Transactional
  default List<D> updateAllDto(Collection<D> dtos, Consumer<E> mutator, Long currentUserId) {
    Objects.requireNonNull(dtos, "DTOs collection cannot be null");
    Specification<E> spec = updateFilterByUserId(currentUserId)
        .and((root, query, cb) -> root.get("id").in(dtos.stream().map(D::getId).collect(Collectors.toList())));
    return svcRead().t().entitiesToDtos(svcRead().findAll(spec).stream()
        .peek(mutator)
        .map(entity -> svcUpdate().update(entity, e -> {
        }))
        .collect(Collectors.toList()));
  }

  @Transactional
  default List<D> updateAllAndFlushDto(Collection<D> dtos, Consumer<E> mutator, Long currentUserId) {
    Objects.requireNonNull(dtos, "DTOs collection cannot be null");
    Specification<E> spec = updateFilterByUserId(currentUserId)
        .and((root, query, cb) -> root.get("id").in(dtos.stream().map(D::getId).collect(Collectors.toList())));
    return svcRead().t().entitiesToDtos(svcRead().findAll(spec).stream()
        .peek(mutator)
        .map(entity -> svcUpdate().updateAndFlush(entity, e -> {
        }))
        .collect(Collectors.toList()));
  }

  // endregion
}
