package base.controller.abstractions;

import base.service.abstractions.BaseOwnService;


/**
 * Базовая абстракция для любого контроллера
 *
 * @param <D> Тип дто
 * @param <E> Тип Сущности
 * @author Ivan Zhendorenko
 */
public interface BaseOwnController<D, E> {

  /**
   * Сервис, используемый контроллером
   *
   * @return Сервис
   */
  BaseOwnService<D, ? extends E> svcOwn();
}
