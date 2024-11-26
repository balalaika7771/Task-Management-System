package base.controller.crud;

import base.controller.abstractions.BaseController;
import base.service.CreateService;
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
 * Контроллер для пакетного сохранения сущностей.
 *
 * @param <D> Тип DTO
 * @param <E> Тип сущности
 * @param <I> Тип идентификатора сущности
 * @author Ivan Zhendorenko
 */
public interface MultiplyCreateController<D, E, I> extends BaseController<D, E> {

  /**
   * Возвращает сервис для операций создания.
   *
   * @return Сервис для операций создания
   */
  default CreateService<D, ? extends E, I> svcCreate() {
    return (CreateService<D, ? extends E, I>) svc();
  }

  /**
   * Пакетное сохранение коллекции сущностей.
   *
   * @param dtos  Коллекция DTO для сохранения
   * @param dummy Заглушка для проверки прав доступа
   * @return Список сохраненных DTO
   */
  @Operation(
      summary = "Сохранение коллекции сущностей",
      description = "Сохраняет коллекцию переданных DTO в базе данных."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Коллекция успешно сохранена."),
      @ApiResponse(responseCode = "400", description = "Ошибка валидации входных данных."),
      @ApiResponse(responseCode = "403", description = "Недостаточно прав для выполнения операции.")
  })
  @PreAuthorize("hasPermission(#dummy, 'ALL_W') or hasPermission(#dummy, 'ADM')")
  @PostMapping("/save-all")
  default List<D> saveAll(
      @RequestBody
      @Valid
      @Parameter(description = "Коллекция DTO для сохранения", required = true) Collection<D> dtos,
      @Parameter(hidden = true) E dummy) {
    return svcCreate().saveAllDto(dtos);
  }
}
