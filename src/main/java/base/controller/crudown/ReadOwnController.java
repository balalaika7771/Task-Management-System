package base.controller.crudown;

import base.util.Auth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
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


public interface ReadOwnController<D, E, I> extends SearchOwnController<D, E, I> {

  @Operation(summary = "Поиск",
      description = "Поиск сущности по идентификатору",
      parameters = @Parameter(name = "id", description = "Идентификатор сущности", required = true)
  )
  @PreAuthorize("hasPermission(#dummy, 'OWN_R') or hasPermission(#dummy, 'ADM')")
  @GetMapping("/find-by-id-own/{id}")
  default Optional<D> findByIdOwn(@PathVariable I id, @Parameter(hidden = true) E dummy) {
    return svcOwnRead().findByIdDto(id, Auth.getCurrentUserId());
  }

  @Operation(summary = "Поиск",
      description = "Поиск сущностей по идентификаторам",
      parameters = @Parameter(name = "ids", description = "Идентификаторы искомых сущностей в виде id1,id2,id3", required = true)
  )
  @PreAuthorize("hasPermission(#dummy, 'OWN_R') or hasPermission(#dummy, 'ADM')")
  @GetMapping("/find-all-by-id-own")
  default List<D> findAllByIdOwn(@RequestParam List<I> ids, @Parameter(hidden = true) E dummy) {
    return svcOwnRead().findAllByIdDto(ids, Auth.getCurrentUserId());
  }

  @Operation(summary = "Поиск сущностей с поддержкой пагинации",
      description = "Поиск сущностей с поддержкой пагинации",
      parameters = {
          @Parameter(name = "page", description = "Порядковый номер нужной страницы", schema = @Schema(example = "0")),
          @Parameter(name = "size", description = "Количество элементов на странице", schema = @Schema(example = "20")),
          @Parameter(name = "sort", description = """
              Сортировка элементов на странице вида property1,property2(,asc|desc). Например ?sort=field1&sort=field2,asc
              """),
      }
  )
  @PreAuthorize("hasPermission(#dummy, 'OWN_R') or hasPermission(#dummy, 'ADM')")
  @GetMapping("/find-all-own")
  default Page<D> findAllOwn(@Parameter(hidden = true) @ParameterObject @PageableDefault Pageable pageable, @Parameter(hidden = true) E dummy) {
    return svcOwnRead().findAllDto(pageable, Auth.getCurrentUserId());
  }
}
