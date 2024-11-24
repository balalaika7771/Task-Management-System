package base.controller.crud;


import base.service.CreateService;


/**
 * Контроллер с Create-операциями
 *
 * @author Ivan Zhendorenko
 */
public interface CreateController<D, E, I> extends SingleCreateController<D, E, I>, MultiplyCreateController<D, E, I> {

  @Override
  default CreateService<D, ? extends E, I> svcCreate() {
    return SingleCreateController.super.svcCreate();
  }
}
