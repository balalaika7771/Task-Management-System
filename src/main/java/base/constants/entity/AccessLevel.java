package base.constants.entity;

/**
 * Уровень доступа к entity
 *
 * @author Ivan Zhendorenko
 */
public enum AccessLevel {
  ALL_W,  // Полный доступ на запись
  ALL_R,  // Полный доступ на чтение
  OWN_W,  // Доступ на запись только своих записей
  OWN_R,  // Доступ на чтение только своих записей
  ADM;    // Административный доступ

  /**
   * Проверяет, является ли уровень доступа уровнем записи.
   *
   * @return true, если доступ подразумевает возможность записи.
   */
  public boolean isWrite() {
    return this == ALL_W || this == OWN_W || this == ADM;
  }

  /**
   * Проверяет, является ли уровень доступа уровнем чтения.
   *
   * @return true, если доступ подразумевает возможность чтения.
   */
  public boolean isRead() {
    return this == ALL_R || this == OWN_R || this == ADM;
  }
}

