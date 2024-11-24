package base.service.crudown;

import base.service.ReadService;
import base.service.abstractions.BaseOwnService;
import base.util.Data;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.experimental.ExtensionMethod;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;


/**
 * Сервис, способный отдавать сущности, если пользователь имеет доступ
 *
 * @param <D> Тип дто
 * @param <E> Тип Сущности
 * @param <I> Тип идентификатора сущности
 */

@ExtensionMethod({Data.class})
public interface ReadOwnService<D, E, I> extends BaseOwnService<D, E> {

  default ReadService<D, E, I> svcRead() {
    return (ReadService<D, E, I>) svc();
  }

  /**
   * Возвращает спецификацию для проверки прав текущего пользователя на доступ к сущности.
   *
   * @param currentUserId ID текущего пользователя.
   * @return спецификация для проверки прав.
   */
  Specification<E> readFilterByUserId(Long currentUserId);

  // region DTO

  default Optional<D> findByIdDto(I id, Long currentUserId) {
    var spec = readFilterByUserId(currentUserId)
        .and((root, query, cb) -> cb.equal(root.get("id"), id));
    return svcRead().findOneDto(spec);
  }

  default List<D> findAllByIdDto(Iterable<I> ids, Long currentUserId) {
    var spec = readFilterByUserId(currentUserId)
        .and((root, query, cb) -> root.get("id").in(ids));
    return svcRead().findAllDto(spec);
  }

  default List<D> findAllDto(Sort sort, Long currentUserId) {
    var spec = readFilterByUserId(currentUserId);
    return svcRead().findAllDto(spec).stream()
        .sorted((o1, o2) -> sort.getOrderFor("id") != null && sort.getOrderFor("id").isAscending()
            ? ((Comparable) o1).compareTo(o2)
            : ((Comparable) o2).compareTo(o1))
        .collect(Collectors.toList());
  }

  default Page<D> findAllDto(Pageable pageable, Long currentUserId) {
    var spec = readFilterByUserId(currentUserId);
    return svcRead().findAllDto(spec, pageable);
  }

  default List<D> findAllDto(Specification<E> spec, Long currentUserId) {
    var combinedSpec = spec.and(readFilterByUserId(currentUserId));
    return svcRead().findAllDto(combinedSpec);
  }

  default Page<D> findAllDto(Specification<E> spec, Pageable pageable, Long currentUserId) {
    var combinedSpec = spec.and(readFilterByUserId(currentUserId));
    return svcRead().findAllDto(combinedSpec, pageable);
  }

  default Page<D> findAllDto(String search, boolean isDistinct, Pageable pageable, Long currentUserId) {
    var searchSpec = search.searchToSpec(svcRead().searchPathReplacer(), isDistinct);
    var combinedSpec = searchSpec.and(readFilterByUserId(currentUserId));
    return svcRead().findAllDto(combinedSpec, pageable);
  }

  default Optional<D> findOneDto(Specification<E> spec, Long currentUserId) {
    var combinedSpec = spec.and(readFilterByUserId(currentUserId));
    return svcRead().findOneDto(combinedSpec);
  }

  default Optional<D> findOneDto(Example<E> example, Long currentUserId) {
    var spec = readFilterByUserId(currentUserId);
    return svcRead().findOneDto(spec);
  }

  default Iterable<D> findAllDto(Example<E> example, Long currentUserId) {
    var spec = readFilterByUserId(currentUserId);
    return svcRead().findAllDto(spec);
  }

  default Iterable<D> findAllDto(Example<E> example, Sort sort, Long currentUserId) {
    var spec = readFilterByUserId(currentUserId);
    List<D> result = svcRead().findAllDto(spec);
    return result.stream()
        .sorted((o1, o2) -> sort.getOrderFor("id") != null && sort.getOrderFor("id").isAscending()
            ? ((Comparable) o1).compareTo(o2)
            : ((Comparable) o2).compareTo(o1))
        .collect(Collectors.toList());
  }

  default Page<D> findAllDto(Example<E> example, Pageable pageable, Long currentUserId) {
    var spec = readFilterByUserId(currentUserId);
    return svcRead().findAllDto(spec, pageable);
  }
  // endregion
}
