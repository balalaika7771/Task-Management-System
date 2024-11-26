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
 * Контроллер с Update-операциями для сущностей, связанных с текущим пользователем.
 *
 * @param <D> Тип DTO
 * @param <E> Тип сущности
 * @param <I> Тип идентификатора
 * @author Ivan Zhendorenko
 */
public interface UpdateOwnController<D extends IdentifiableDto<D>, E, I> extends BaseOwnController<D, E> {

  /**
   * Возвращает сервис для выполнения операций обновления собственных сущностей.
   *
   * @return Сервис обновления
   */
  default UpdateOwnService<D, ? extends E, I> svcOwnUpdate() {
    return (UpdateOwnService<D, ? extends E, I>) svcOwn();
  }

  /**
   * Обновление сущности, связанной с текущим пользователем.
   *
   * @param dto   DTO обновляемой сущности
   * @param dummy Заглушка для проверки прав доступа
   * @return Обновленная сущность в виде DTO
   */
  @Operation(summary = "Обновление собственной сущности",
      description = """
              Выполняет обновление существующей сущности, связанной с текущим пользователем.
                                  
              Операция доступна для пользователей с правами 'OWN_W' или 'ADM'. 
              Перед обновлением выполняется валидация входного DTO.
          """,
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "DTO обновляемой сущности. Обязательные поля должны быть заполнены.",
          required = true
      )
  )
  @PreAuthorize("hasPermission(#dummy, 'OWN_W') or hasPermission(#dummy, 'ADM')")
  @PatchMapping("/update-own")
  default D updateOwn(@RequestBody @Valid D dto, @Parameter(hidden = true) E dummy) {
    return svcOwnUpdate().updateDto(dto, e -> {
      // Место для пользовательской логики изменения сущности перед обновлением
    }, Auth.getCurrentUserId());
  }
}
