
package base.service.abstractions;

/**
 * Базовая абстракция для сервиса с проверкой доступа
 *
 * @param <D> Тип дто
 * @param <E> Тип Сущности
 * @author Ivan Zhendorenko
 */
public interface BaseOwnService<D, E> {


  /**
   * Сервис доступа к модели
   *
   * @return Сервис
   */
  BaseService<D, ? extends E> svc();
}
