package base.controller.crudown;

import base.controller.abstractions.BaseOwnController;
import base.service.crudown.CreateOwnService;
import base.util.Auth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.Collection;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Контроллер для пакетного сохранения сущностей, доступных текущему пользователю.
 *
 * @param <D> Тип DTO
 * @param <E> Тип сущности
 * @param <I> Тип идентификатора сущности
 * @author Ivan Zhendorenko
 */
public interface MultiplyOwnCreateController<D, E, I> extends BaseOwnController<D, E> {

  /**
   * Возвращает сервис для операций создания с учетом прав доступа.
   *
   * @return Сервис для операций создания
   */
  default CreateOwnService<D, ? extends E, I> svcOwnCreate() {
    return (CreateOwnService<D, ? extends E, I>) svcOwn();
  }

  /**
   * Пакетное сохранение коллекции сущностей, доступных текущему пользователю.
   *
   * @param dtos  Коллекция DTO для сохранения
   * @param dummy Заглушка для проверки прав доступа
   * @return Список сохраненных DTO
   */
  @Operation(
      summary = "Пакетное сохранение коллекции",
      description = "Сохраняет коллекцию переданных DTO, доступных текущему пользователю."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Коллекция успешно сохранена."),
      @ApiResponse(responseCode = "400", description = "Ошибка валидации входных данных."),
      @ApiResponse(responseCode = "403", description = "Недостаточно прав для выполнения операции.")
  })
  @PreAuthorize("hasPermission(#dummy, 'OWN_W') or hasPermission(#dummy, 'ADM')")
  @PostMapping("/save-all-own")
  default List<D> saveAllOwn(
      @RequestBody
      @Valid
      @Parameter(description = "Коллекция DTO для сохранения", required = true) Collection<D> dtos,
      @Parameter(hidden = true) E dummy) {
    return svcOwnCreate().saveAllDto(dtos, Auth.getCurrentUserId());
  }
}
