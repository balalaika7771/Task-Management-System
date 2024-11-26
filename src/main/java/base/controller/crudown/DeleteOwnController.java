package base.controller.crudown;

import base.abstractions.Identifiable;
import base.controller.abstractions.BaseOwnController;
import base.service.crudown.DeleteOwnService;
import base.util.Auth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.Collection;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Контроллер с Delete-операциями для владельца сущностей.
 *
 * @param <D> Тип DTO
 * @param <E> Тип сущности, должен реализовывать {@link Identifiable}
 * @param <I> Тип идентификатора сущности
 * @author Ivan Zhendorenko
 */
public interface DeleteOwnController<D, E extends Identifiable<E>, I> extends BaseOwnController<D, E> {

  /**
   * Возвращает сервис, предоставляющий операции удаления для владельца.
   *
   * @return Сервис удаления
   */
  default DeleteOwnService<D, ? extends E, I> svcDelete() {
    return (DeleteOwnService<D, ? extends E, I>) svcOwn();
  }

  /**
   * Удаляет сущность, принадлежащую текущему пользователю, по её идентификатору.
   *
   * @param id    Идентификатор удаляемой сущности
   * @param dummy Заглушка для проверки прав доступа
   */
  @Operation(
      summary = "Удаление сущности владельцем",
      description = "Удаляет сущность по указанному идентификатору, если пользователь имеет права на неё."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Сущность успешно удалена."),
      @ApiResponse(responseCode = "404", description = "Сущность с указанным идентификатором не найдена."),
      @ApiResponse(responseCode = "403", description = "Недостаточно прав для удаления сущности.")
  })
  @PreAuthorize("hasPermission(#dummy, 'OWN_W') or hasPermission(#dummy, 'ADM')")
  @DeleteMapping("/delete-by-id-own/{id}")
  default void deleteByIdOwn(
      @PathVariable
      @Parameter(description = "Идентификатор удаляемой сущности", required = true) I id,
      @Parameter(hidden = true) E dummy) {
    svcDelete().deleteById(id, Auth.getCurrentUserId());
  }

  /**
   * Удаляет несколько сущностей, принадлежащих текущему пользователю, по их идентификаторам.
   *
   * @param ids   Коллекция идентификаторов удаляемых сущностей
   * @param dummy Заглушка для проверки прав доступа
   */
  @Operation(
      summary = "Удаление нескольких сущностей владельцем",
      description = "Удаляет несколько сущностей по указанным идентификаторам, если пользователь имеет права на них."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Сущности успешно удалены."),
      @ApiResponse(responseCode = "404", description = "Некоторые сущности с указанными идентификаторами не найдены."),
      @ApiResponse(responseCode = "403", description = "Недостаточно прав для удаления сущностей.")
  })
  @PreAuthorize("hasPermission(#dummy, 'OWN_W') or hasPermission(#dummy, 'ADM')")
  @DeleteMapping("/delete-all-by-id-own/{ids}")
  default void deleteAllByIdOwn(
      @PathVariable
      @Parameter(description = "Идентификаторы удаляемых сущностей в формате id1,id2,id3", required = true) Collection<I> ids,
      @Parameter(hidden = true) E dummy) {
    svcDelete().deleteAllById(ids, Auth.getCurrentUserId());
  }
}
