package base.controller.crudown;

import base.controller.abstractions.BaseOwnController;
import base.service.crudown.CreateOwnService;
import base.util.Auth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import java.util.Collection;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * Контроллер с пакетной create-операцией
 *
 * @author Ivan Zhendorenko
 */
public interface MultiplyOwnCreateController<D, E, I> extends BaseOwnController<D, E> {

  /**
   * Сервис, используемый контроллером
   *
   * @return Сервис
   */
  default CreateOwnService<D, ? extends E, I> svcOwnCreate() {
    return (CreateOwnService<D, ? extends E, I>) svcOwn();
  }

  @Operation(summary = "Сохранение коллекции",
      description = "Сохранение коллекции сущностей",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Коллекция с сохраняемыми сущностью",
          required = true
      )
  )
  @PreAuthorize("hasPermission(#dummy, 'OWN_W') or hasPermission(#dummy, 'ADM')")
  @PostMapping("/save-all-own")
  default List<D> saveAllOwn(@RequestBody @Valid Collection<D> dtos, @Parameter(hidden = true) E dummy) {
    return svcOwnCreate().saveAllDto(dtos, Auth.getCurrentUserId());
  }
}
