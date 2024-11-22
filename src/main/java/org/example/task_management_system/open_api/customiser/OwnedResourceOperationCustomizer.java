package org.example.task_management_system.open_api.customiser;

import base.abstractions.Owned;
import base.annotation.OwnedResource;
import io.swagger.v3.oas.models.Operation;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;


@Component
public class OwnedResourceOperationCustomizer implements OperationCustomizer {

  @Override
  public Operation customize(Operation operation, HandlerMethod handlerMethod) {
    // Проверяем, есть ли аннотация @OwnedResource на методе
    OwnedResource ownedResource = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), OwnedResource.class);
    if (ownedResource != null) {
      operation.addExtension("x-owned-resource", true);

      // Проверяем, является ли параметр dummy подклассом Owned
      Class<?>[] parameterTypes = handlerMethod.getMethod().getParameterTypes();
      for (Class<?> paramType : parameterTypes) {
        if (Owned.class.isAssignableFrom(paramType)) {
          operation.addExtension("x-dummy-is-owned", true);
          return operation;
        }
      }

      operation.addExtension("x-dummy-is-owned", false);
    }
    return operation;
  }
}

