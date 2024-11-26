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
 * Контроллер для выполнения одиночной операции сохранения сущности с проверкой прав доступа текущего пользователя.
 *
 * @param <D> Тип DTO
 * @param <E> Тип сущности
 * @param <I> Тип идентификатора
 * @author Ivan Zhendorenko
 */
public interface SingleCreateOwnController<D, E, I> extends BaseOwnController<D, E> {

  /**
   * Возвращает сервис для выполнения операции создания с учетом прав текущего пользователя.
   *
   * @return Сервис для создания
   */
  default CreateOwnService<D, ? extends E, I> svcOwnCreate() {
    return (CreateOwnService<D, ? extends E, I>) svcOwn();
  }

  /**
   * Выполняет операцию сохранения сущности с учетом прав доступа текущего пользователя.
   *
   * @param dto   DTO сохраняемой сущности
   * @param dummy Заглушка для проверки прав доступа
   * @return Сохраненная сущность в виде DTO
   */
  @Operation(summary = "Сохранение сущности текущим пользователем",
      description = """
              Сохраняет новую сущность в базе данных с учетом прав текущего пользователя.
                                  
              Перед сохранением осуществляется проверка прав доступа и валидация DTO. 
              Доступно для пользователей с правами 'OWN_W' или 'ADM'.
          """,
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "DTO сохраняемой сущности. Все обязательные поля должны быть заполнены.",
          required = true
      )
  )
  @PreAuthorize("hasPermission(#dummy, 'OWN_W') or hasPermission(#dummy, 'ADM')")
  @PostMapping("/save-own")
  default D saveOwn(@RequestBody @Valid D dto, @Parameter(hidden = true) E dummy) {
    return svcOwnCreate().saveDto(dto, Auth.getCurrentUserId());
  }
}
