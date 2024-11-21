package base.controller.crud;

import base.controller.abstractions.BaseController;
import base.service.CrudService;
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
public interface SingleCreateController<D, E, I> extends BaseController<D, E> {

  /**
   * Сервис, используемый контроллером
   *
   * @return Сервис
   */
  @Override
  CrudService<D, ? extends E, I> svc();

  @Operation(summary = "Сохранение",
      description = "Сохранение сущности",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Сохраняемая сущность",
          required = true
      )
  )
  @PreAuthorize("hasPermission(#dummy, 'ALL_W') or hasPermission(#dummy, 'ADM')")
  @PostMapping("/save")
  default D save(@RequestBody @Valid D dto, @Parameter(hidden = true) E dummy) {
    return svc().saveDto(dto);
  }
}
