package base.controller.crud;

import base.controller.abstractions.BaseController;
import base.service.ReadService;
import base.util.Data;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.ExtensionMethod;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Контроллер для выполнения операций поиска сущностей по фильтрам.
 *
 * @param <D> Тип DTO
 * @param <E> Тип сущности
 * @param <I> Тип идентификатора сущности
 * @author Ivan Zhendorenko
 */
@ExtensionMethod({Data.class})
public interface SearchController<D, E, I> extends BaseController<D, E> {

  /**
   * Сервис, используемый контроллером
   *
   * @return Сервис для операций чтения
   */
  default ReadService<D, ? extends E, I> svcRead() {
    return (ReadService<D, ? extends E, I>) svc();
  }

  /**
   * Поиск сущностей по фильтрам.
   *
   * @param search     Строка условий для фильтрации сущностей
   * @param isDistinct Флаг для вывода только уникальных значений
   * @param pageable   Объект, описывающий параметры пагинации
   * @param dummy      Заглушка для проверки прав доступа
   * @return Страница с DTO сущностей
   */
  @SuppressWarnings("unchecked")
  @Operation(summary = "Поиск сущностей по фильтрам",
      description = """
              Выполняет поиск сущностей на основе переданных фильтров.
                              
              Логическая операция между условиями — AND.
                              
              Поддерживаемые операции фильтрации:
                                  
              - **Эквиваленция** (`:`): Поиск по точному соответствию значения.
              - **Отрицание** (`!`): Исключение записей с указанным значением.
              - **Больше, чем** (`>`): Поиск записей с значением больше указанного.
              - **Меньше, чем** (`<`): Поиск записей с значением меньше указанного.
              - **Соответствие шаблону** (`~`): Частичное совпадение, например, поиск по подстроке.
                                  
              **Примеры запросов**:
              - `?search=name:Joe,age>14.1,lastName!Doe,email~gmail`
              - `?search=departments.title~Отдел,users.id:0`
              - `?search=startExaminationDate>2023-07-19,startExaminationDate<2023-07-21`
                                  
              **Сортировка**:
              Для добавления сортировки используйте параметр `sort` в формате `property1,property2(,asc|desc)`. Например:
              - `?sort=field1,asc`
              - `?sort=field2`
          """,
      parameters = {
          @Parameter(name = "search", description = "Условия фильтрации в виде строки", required = true),
          @Parameter(name = "isDistinct", description = "Флаг для возвращения только уникальных записей", schema = @Schema(example = "true")),
          @Parameter(name = "page", description = "Номер страницы для пагинации", schema = @Schema(example = "0")),
          @Parameter(name = "size", description = "Размер страницы для пагинации", schema = @Schema(example = "20")),
          @Parameter(name = "sort", description = """
              Укажите параметры сортировки, разделенные запятыми. Например:
              - `field1,field2,asc` — сортировка по возрастанию.
              - `field1,desc` — сортировка по убыванию.
              """)
      }
  )
  @PreAuthorize("hasPermission(#dummy, 'ALL_R') or hasPermission(#dummy, 'ADM')")
  @GetMapping("/search")
  default Page<D> search(
      @RequestParam(value = "search")
      @Parameter(description = "Строка с фильтрами поиска", required = true) String search,
      @RequestParam(value = "isDistinct", required = false, defaultValue = "false")
      @Parameter(description = "Флаг для возврата только уникальных записей", schema = @Schema(example = "false")) boolean isDistinct,
      @Parameter(hidden = true)
      @ParameterObject
      @PageableDefault Pageable pageable,
      @Parameter(hidden = true) E dummy) {
    var spec = search.searchToSpec(svcRead().searchPathReplacer(), isDistinct);
    return svcRead().findAllDto(spec, pageable);
  }
}
