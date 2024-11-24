package base.service.crudown;

import base.service.CreateService;
import base.service.abstractions.BaseOwnService;
import java.util.Collection;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;


/**
 * Сервис, способный создавать сущности, если пользователь имеет доступ
 *
 * @param <D> Тип дто
 * @param <E> Тип Сущности
 * @param <I> Тип идентификатора сущности
 */
public interface CreateOwnService<D, E, I> extends BaseOwnService<D, E> {

  default CreateService<D, ? extends E, I> svcCreate() {
    return (CreateService<D, ? extends E, I>) svc();
  }

  /**
   * Проверяет доступ на создание сущности.
   *
   * @param d             DTO объекта
   * @param currentUserId ID текущего пользователя
   * @throws SecurityException если доступ запрещен
   */
  void checkCreateAccess(D d, Long currentUserId);

  // region DTO

  @Transactional
  default D saveDto(D d, Long currentUserId) {
    checkCreateAccess(d, currentUserId);
    return svcCreate().saveDto(d);
  }

  @Transactional
  default D saveAndFlushDto(D d, Long currentUserId) {
    checkCreateAccess(d, currentUserId);
    return svcCreate().saveAndFlushDto(d);
  }

  @Transactional
  default List<D> saveAllDto(Collection<D> ds, Long currentUserId) {
    ds.forEach(d -> checkCreateAccess(d, currentUserId));
    return svcCreate().saveAllDto(ds);
  }

  @Transactional
  default List<D> saveAllAndFlushDto(Collection<D> ds, Long currentUserId) {
    ds.forEach(d -> checkCreateAccess(d, currentUserId));
    return svcCreate().saveAllAndFlushDto(ds);
  }

  // endregion
}

