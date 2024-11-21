package base.controller.crud;

import base.controller.abstractions.BaseController;
import base.service.CrudService;
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
public interface UpdateController<D, E, I> extends BaseController<D, E> {


  /**
   * Сервис, используемый контроллером
   *
   * @return Сервис
   */
  @Override
  CrudService<D, ? extends E, I> svc();

  @Operation(summary = "Обновление",
      description = "Обновление сущности",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Обновляемая сущность",
          required = true
      )
  )
  @PreAuthorize("hasPermission(#dummy, 'ALL_W') or hasPermission(#dummy, 'ADM')")
  @PatchMapping("/update")
  default D update(@RequestBody @Valid D dto, @Parameter(hidden = true) E dummy) {
    return svc().updateDto(dto, e -> {
    });
  }
}
