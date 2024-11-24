package base.controller.crudown;

import base.controller.abstractions.BaseOwnController;
import base.service.crudown.CreateOwnService;
import base.util.Auth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * Контроллер с одиночной create-операцией
 *
 * @author Ivan Zhendorenko
 */
public interface SingleCreateOwnController<D, E, I> extends BaseOwnController<D, E> {

  /**
   * Сервис, используемый контроллером
   *
   * @return Сервис
   */
  default CreateOwnService<D, ? extends E, I> svcOwnCreate() {
    return (CreateOwnService<D, ? extends E, I>) svcOwn();
  }

  @Operation(summary = "Сохранение",
      description = "Сохранение сущности",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Сохраняемая сущность",
          required = true
      )
  )
  @PreAuthorize("hasPermission(#dummy, 'OWN_W') or hasPermission(#dummy, 'ADM')")
  @PostMapping("/save-own")
  default D saveOwn(@RequestBody @Valid D dto, @Parameter(hidden = true) E dummy) {
    return svcOwnCreate().saveDto(dto, Auth.getCurrentUserId());
  }
}
