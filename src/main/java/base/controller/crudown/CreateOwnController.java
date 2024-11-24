package base.controller.crudown;


import base.service.crudown.CreateOwnService;


/**
 * Контроллер с Create-операциями
 *
 * @author Ivan Zhendorenko
 */
public interface CreateOwnController<D, E, I> extends SingleCreateOwnController<D, E, I>, MultiplyOwnCreateController<D, E, I> {

  @Override
  default CreateOwnService<D, ? extends E, I> svcOwnCreate() {
    return SingleCreateOwnController.super.svcOwnCreate();
  }
}
