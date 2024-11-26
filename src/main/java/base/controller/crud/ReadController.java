package base.controller.crud;

import base.controller.abstractions.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Интерфейс контроллера для операций чтения сущностей.
 *
 * @param <D> Тип DTO
 * @param <E> Тип сущности
 * @param <I> Тип идентификатора
 */
public interface ReadController<D, E, I> extends BaseController<D, E>, SearchController<D, E, I> {

  /**
   * Поиск сущности по идентификатору.
   *
   * @param id    Идентификатор сущности
   * @param dummy Заглушка для проверки прав доступа
   * @return DTO сущности
   */
  @Operation(
      summary = "Поиск сущности по идентификатору",
      description = "Возвращает сущность на основании её идентификатора."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Сущность найдена и возвращена."),
      @ApiResponse(responseCode = "404", description = "Сущность с указанным идентификатором не найдена."),
      @ApiResponse(responseCode = "403", description = "Доступ к ресурсу запрещен.")
  })
  @PreAuthorize("hasPermission(#dummy, 'ALL_R') or hasPermission(#dummy, 'ADM')")
  @GetMapping("/find-by-id/{id}")
  default Optional<D> findById(
      @PathVariable
      @Parameter(description = "Идентификатор сущности", required = true) I id,
      @Parameter(hidden = true) E dummy) {
    return svcRead().findByIdDto(id);
  }

  /**
   * Поиск сущностей по идентификаторам.
   *
   * @param ids   Список идентификаторов
   * @param dummy Заглушка для проверки прав доступа
   * @return Список DTO сущностей
   */
  @Operation(
      summary = "Поиск сущностей по идентификаторам",
      description = "Возвращает список сущностей по их идентификаторам."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Сущности найдены и возвращены."),
      @ApiResponse(responseCode = "404", description = "Одна или несколько сущностей не найдены."),
      @ApiResponse(responseCode = "403", description = "Доступ к ресурсу запрещен.")
  })
  @PreAuthorize("hasPermission(#dummy, 'ALL_R') or hasPermission(#dummy, 'ADM')")
  @GetMapping("/find-all-by-id")
  default List<D> findAllById(
      @RequestParam
      @Parameter(description = "Список идентификаторов сущностей", required = true) List<I> ids,
      @Parameter(hidden = true) E dummy) {
    return svcRead().findAllByIdDto(ids);
  }

  /**
   * Поиск сущностей с поддержкой пагинации.
   *
   * @param pageable Объект, описывающий параметры пагинации
   * @param dummy    Заглушка для проверки прав доступа
   * @return Страница с DTO сущностей
   */
  @Operation(
      summary = "Поиск сущностей с поддержкой пагинации",
      description = "Возвращает страницу с сущностями на основе переданных параметров пагинации."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Страница с сущностями успешно возвращена."),
      @ApiResponse(responseCode = "403", description = "Доступ к ресурсу запрещен.")
  })
  @PreAuthorize("hasPermission(#dummy, 'ALL_R') or hasPermission(#dummy, 'ADM')")
  @GetMapping("/find-all")
  default Page<D> findAll(
      @ParameterObject
      @PageableDefault
      @Parameter(description = "Параметры пагинации (номер страницы, размер страницы, сортировка)", hidden = true) Pageable pageable,
      @Parameter(hidden = true) E dummy) {
    return svcRead().findAllDto(pageable);
  }
}
