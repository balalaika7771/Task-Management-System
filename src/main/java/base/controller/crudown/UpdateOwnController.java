package base.controller.crudown;

import base.abstractions.IdentifiableDto;
import base.controller.abstractions.BaseOwnController;
import base.service.crudown.UpdateOwnService;
import base.util.Auth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * Контроллер с Update-операциями
 *
 * @author Ivan Zhendorenko
 */
public interface UpdateOwnController<D extends IdentifiableDto<D>, E, I> extends BaseOwnController<D, E> {

  /**
   * Сервис, используемый контроллером
   *
   * @return Сервис
   */
  default UpdateOwnService<D, ? extends E, I> svcOwnUpdate() {
    return (UpdateOwnService<D, ? extends E, I>) svcOwn();
  }

  @Operation(summary = "Обновление",
      description = "Обновление сущности",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Обновляемая сущность",
          required = true
      )
  )
  @PreAuthorize("hasPermission(#dummy, 'OWN_W') or hasPermission(#dummy, 'ADM')")
  @PatchMapping("/update-own")
  default D updateOwn(@RequestBody @Valid D dto, @Parameter(hidden = true) E dummy) {
    return svcOwnUpdate().updateDto(dto, e -> {
    }, Auth.getCurrentUserId());
  }
}
