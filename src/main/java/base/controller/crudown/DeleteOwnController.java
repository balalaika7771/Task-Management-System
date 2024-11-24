package base.controller.crudown;

import base.abstractions.Identifiable;
import base.controller.abstractions.BaseOwnController;
import base.service.crudown.DeleteOwnService;
import base.util.Auth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.Collection;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * Контроллер с Delete-операциями
 *
 * @author Ivan Zhendorenko
 */
public interface DeleteOwnController<D, E extends Identifiable<E>, I> extends BaseOwnController<D, E> {

  /**
   * Сервис, используемый контроллером
   *
   * @return Сервис
   */

  default DeleteOwnService<D, ? extends E, I> svcDelete() {
    return (DeleteOwnService<D, ? extends E, I>) svcOwn();
  }

  @Operation(summary = "Удаление",
      description = "Удаление сущности по идентификатору",
      parameters = @Parameter(name = "id", description = "Идентификатор удаляемой сущности", required = true)
  )
  @PreAuthorize("hasPermission(#dummy, 'OWN_W') or hasPermission(#dummy, 'ADM')")
  @DeleteMapping("/delete-by-id-own/{id}")
  default void deleteByIdOwn(@PathVariable I id, @Parameter(hidden = true) E dummy) {
    svcDelete().deleteById(id, Auth.getCurrentUserId());
  }

  @Operation(summary = "Удаление",
      description = "Удаление сущностей по идентификаторам",
      parameters = @Parameter(name = "ids", description = "Идентификаторы удаляемых сущностей в виде id1,id2,id3", required = true)
  )
  @PreAuthorize("hasPermission(#dummy, 'OWN_W') or hasPermission(#dummy, 'ADM')")
  @DeleteMapping("/delete-all-by-id-own/{ids}")
  default void deleteAllByIdOwn(@PathVariable Collection<I> ids, @Parameter(hidden = true) E dummy) {
    svcDelete().deleteAllById(ids, Auth.getCurrentUserId());
  }
}
