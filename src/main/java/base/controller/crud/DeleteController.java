package base.controller.crud;

import base.controller.abstractions.BaseController;
import base.service.DeleteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.Collection;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Интерфейс контроллера, предоставляющий операции удаления (Delete).
 *
 * @param <D> Тип DTO
 * @param <E> Тип сущности
 * @param <I> Тип идентификатора сущности
 * @author Ivan Zhendorenko
 */
public interface DeleteController<D, E, I> extends BaseController<D, E> {

  /**
   * Возвращает сервис, предоставляющий операции удаления.
   *
   * @return Сервис удаления
   */
  default DeleteService<D, ? extends E, I> svcDelete() {
    return (DeleteService<D, ? extends E, I>) svc();
  }

  /**
   * Удаляет сущность по её идентификатору.
   *
   * @param id    Идентификатор удаляемой сущности
   * @param dummy Заглушка для проверки прав доступа
   */
  @Operation(
      summary = "Удаление сущности",
      description = "Удаляет сущность по указанному идентификатору."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Сущность успешно удалена."),
      @ApiResponse(responseCode = "404", description = "Сущность с указанным идентификатором не найдена."),
      @ApiResponse(responseCode = "403", description = "Недостаточно прав для удаления сущности.")
  })
  @PreAuthorize("hasPermission(#dummy, 'ALL_W') or hasPermission(#dummy, 'ADM')")
  @DeleteMapping("/delete-by-id/{id}")
  default void deleteById(
      @PathVariable
      @Parameter(description = "Идентификатор удаляемой сущности", required = true) I id,
      @Parameter(hidden = true) E dummy) {
    svcDelete().deleteById(id);
  }

  /**
   * Удаляет несколько сущностей по их идентификаторам.
   *
   * @param ids   Коллекция идентификаторов удаляемых сущностей
   * @param dummy Заглушка для проверки прав доступа
   */
  @Operation(
      summary = "Удаление нескольких сущностей",
      description = "Удаляет несколько сущностей по указанным идентификаторам."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Сущности успешно удалены."),
      @ApiResponse(responseCode = "404", description = "Некоторые сущности с указанными идентификаторами не найдены."),
      @ApiResponse(responseCode = "403", description = "Недостаточно прав для удаления сущностей.")
  })
  @PreAuthorize("hasPermission(#dummy, 'ALL_W') or hasPermission(#dummy, 'ADM')")
  @DeleteMapping("/delete-all-by-id/{ids}")
  default void deleteAllById(
      @PathVariable
      @Parameter(description = "Идентификаторы удаляемых сущностей в формате id1,id2,id3", required = true) Collection<I> ids,
      @Parameter(hidden = true) E dummy) {
    svcDelete().deleteAllById(ids);
  }
}
