package base.abstractions;

import base.constants.entity.AccessLevel;
import java.util.Set;


public interface Owned {

  /**
   * Возвращает коллекцию идентификаторов пользователей, имеющих доступ к сущности.
   *
   * @return коллекция идентификаторов пользователей.
   */
  Set<Long> getAccessibleUserIds(AccessLevel level);
}
