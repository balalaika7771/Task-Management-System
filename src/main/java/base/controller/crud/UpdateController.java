package base.controller.crud;

import base.controller.abstractions.BaseController;
import base.service.UpdateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * Контроллер с операцией обновления сущностей.
 *
 * @param <D> Тип DTO
 * @param <E> Тип сущности
 * @param <I> Тип идентификатора
 * @author Ivan Zhendorenko
 */
public interface UpdateController<D, E, I> extends BaseController<D, E> {

  /**
   * Возвращает сервис, используемый для выполнения операций обновления.
   *
   * @return Сервис обновления
   */
  default UpdateService<D, ? extends E, I> svcUpdate() {
    return (UpdateService<D, ? extends E, I>) svc();
  }

  /**
   * Обновление сущности.
   *
   * @param dto   DTO обновляемой сущности
   * @param dummy Заглушка для проверки прав доступа
   * @return Обновленная сущность в виде DTO
   */
  @Operation(summary = "Обновление сущности",
      description = """
              Выполняет обновление существующей сущности в базе данных.
                                  
              Операция доступна для пользователей с правами 'ALL_W' или 'ADM'. 
              Перед обновлением выполняется валидация входного DTO.
          """,
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "DTO обновляемой сущности. Обязательные поля должны быть заполнены.",
          required = true
      )
  )
  @PreAuthorize("hasPermission(#dummy, 'ALL_W') or hasPermission(#dummy, 'ADM')")
  @PatchMapping("/update")
  default D update(@RequestBody @Valid D dto, @Parameter(hidden = true) E dummy) {
    return svcUpdate().updateDto(dto, e -> {
      // Место для пользовательской логики мутации сущности перед обновлением
    });
  }
}
