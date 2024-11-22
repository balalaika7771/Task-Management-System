package org.example.task_management_system.open_api.customiser;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import java.util.Iterator;
import java.util.Map;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.stereotype.Component;


@Component
public class HideNonOwnedMethodsCustomizer implements OpenApiCustomiser {

  @Override
  public void customise(io.swagger.v3.oas.models.OpenAPI openApi) {
    Paths paths = openApi.getPaths();
    Iterator<Map.Entry<String, PathItem>> pathIterator = paths.entrySet().iterator();

    while (pathIterator.hasNext()) {
      Map.Entry<String, PathItem> entry = pathIterator.next();
      PathItem pathItem = entry.getValue();

      // Проверяем все операции в пути (GET, POST, etc.)
      boolean shouldHide = pathItem.readOperations().stream()
          .anyMatch(operation -> isOwnedResourceAndNotOwned(operation));

      if (shouldHide) {
        pathIterator.remove(); // Удаляем путь из OpenAPI
      }
    }
  }

  private boolean isOwnedResourceAndNotOwned(Operation operation) {
    Map<String, Object> extensions = operation.getExtensions();
    if (extensions == null) {
      return false;
    }

    // Проверяем наличие кастомного расширения
    Boolean isOwnedResource = (Boolean) extensions.get("x-owned-resource");
    if (isOwnedResource == null || !isOwnedResource) {
      return false;
    }

    // Проверяем, является ли параметр dummy подклассом Owned
    Boolean isOwned = (Boolean) extensions.get("x-dummy-is-owned");
    return isOwned != null && !isOwned;
  }
}
