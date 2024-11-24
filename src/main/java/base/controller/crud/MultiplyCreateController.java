package base.controller.crud;

import base.controller.abstractions.BaseController;
import base.service.CreateService;
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
public interface MultiplyCreateController<D, E, I> extends BaseController<D, E> {

  /**
   * Сервис, используемый контроллером
   *
   * @return Сервис
   */
  default CreateService<D, ? extends E, I> svcCreate() {
    return (CreateService<D, ? extends E, I>) svc();
  }

  @Operation(summary = "Сохранение коллекции",
      description = "Сохранение коллекции сущностей",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Коллекция с сохраняемыми сущностью",
          required = true
      )
  )
  @PreAuthorize("hasPermission(#dummy, 'ALL_W') or hasPermission(#dummy, 'ADM')")
  @PostMapping("/save-all")
  default List<D> saveAll(@RequestBody @Valid Collection<D> dtos, @Parameter(hidden = true) E dummy) {
    return svcCreate().saveAllDto(dtos);
  }
}
