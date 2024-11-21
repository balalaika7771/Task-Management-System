package base.controller.crud;


import base.service.CrudService;


/**
 * Контроллер с CRUD-операциями
 *
 * @author Ivan Zhendorenko
 */
public interface CrudController<D, E, I> extends CudController<D, E, I>, ReadController<D, E, I> {

  /**
   * Сервис, используемый контроллером
   *
   * @return Сервис
   */
  @Override
  CrudService<D, ? extends E, I> svc();
}
