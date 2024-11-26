package base.controller.crud;

import base.controller.abstractions.BaseController;
import base.service.CreateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * Контроллер для выполнения одиночной операции сохранения (create).
 *
 * @param <D> Тип DTO
 * @param <E> Тип сущности
 * @param <I> Тип идентификатора
 * @author Ivan Zhendorenko
 */
public interface SingleCreateController<D, E, I> extends BaseController<D, E> {

  /**
   * Возвращает сервис для выполнения операции создания.
   *
   * @return Сервис для создания
   */
  default CreateService<D, ? extends E, I> svcCreate() {
    return (CreateService<D, ? extends E, I>) svc();
  }

  /**
   * Выполняет операцию сохранения сущности.
   *
   * @param dto   DTO сохраняемой сущности
   * @param dummy Заглушка для проверки прав доступа
   * @return Сохраненная сущность в виде DTO
   */
  @Operation(summary = "Сохранение сущности",
      description = """
              Сохраняет новую сущность в базе данных. 
                                  
              Перед сохранением осуществляется валидация DTO. 
              Доступен только для пользователей с правами 'ALL_W' или 'ADM'.
          """,
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "DTO сохраняемой сущности. Все обязательные поля должны быть заполнены.",
          required = true
      )
  )
  @PreAuthorize("hasPermission(#dummy, 'ALL_W') or hasPermission(#dummy, 'ADM')")
  @PostMapping("/save")
  default D save(@RequestBody @Valid D dto, @Parameter(hidden = true) E dummy) {
    return svcCreate().saveDto(dto);
  }
}
