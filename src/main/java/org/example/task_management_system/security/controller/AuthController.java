package org.example.task_management_system.security.controller;

import base.constants.entity.EntityName;
import base.controller.abstractions.BaseController;
import base.service.abstractions.BaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.example.task_management_system.core.dto.UserDto;
import org.example.task_management_system.core.exception.EntityNotFoundException;
import org.example.task_management_system.security.business_entity.UserForAuth;
import org.example.task_management_system.security.request.AuthRequest;
import org.example.task_management_system.security.request.RegisterRequest;
import org.example.task_management_system.security.response.AuthResponse;
import org.example.task_management_system.security.service.UserAuthService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Контроллер для обработки запросов, связанных с аутентификацией и авторизацией пользователей.
 * Предоставляет методы для регистрации, авторизации и получения информации о текущем пользователе.
 *
 * @author Ivan Zhendorenko
 */
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Tag(name = "Authentication", description = "API для аутентификации и авторизации пользователей")
public class AuthController implements BaseController<UserDto, UserForAuth> {

  private final UserAuthService userAuthService;

  /**
   * Регистрация нового пользователя.
   *
   * @param authRequest данные для регистрации пользователя.
   * @return объект {@link AuthResponse} с JWT-токеном и именем пользователя.
   */
  @Operation(summary = "Регистрация пользователя", description = "Создает нового пользователя с уникальным именем и возвращает JWT-токен.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Пользователь успешно зарегистрирован, возвращен JWT-токен"),
      @ApiResponse(responseCode = "400", description = "Ошибка валидации запроса")
  })
  @PostMapping("/register")
  public AuthResponse register(
      @Parameter(description = "Данные пользователя для регистрации", required = true)
      @RequestBody RegisterRequest authRequest) {
    return new AuthResponse(userAuthService.register(authRequest.toUserDto()), authRequest.username());
  }

  /**
   * Авторизация пользователя.
   *
   * @param authRequest учетные данные для входа.
   * @return объект {@link AuthResponse} с JWT-токеном и именем пользователя.
   */
  @Operation(summary = "Авторизация пользователя", description = "Авторизует пользователя и возвращает JWT-токен для последующего доступа.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Успешная авторизация, возвращен JWT-токен"),
      @ApiResponse(responseCode = "401", description = "Неверные учетные данные")
  })
  @PostMapping("/login")
  public AuthResponse login(
      @Parameter(description = "Учетные данные пользователя для входа", required = true)
      @RequestBody AuthRequest authRequest) {
    return new AuthResponse(userAuthService.login(authRequest.toUserDto()), authRequest.usernameOrEmail());
  }

  /**
   * Получение информации о текущем авторизованном пользователе.
   *
   * @param authentication объект {@link Authentication}, содержащий данные о текущем пользователе.
   * @return объект {@link UserDto} с информацией о текущем пользователе.
   */
  @Operation(summary = "Получение информации о текущем пользователе", description = "Возвращает данные текущего авторизованного пользователя.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Информация о пользователе возвращена"),
      @ApiResponse(responseCode = "401", description = "Пользователь не авторизован")
  })
  @GetMapping("/who_am_i")
  public UserDto whoAmI(Authentication authentication) {
    String username = authentication.getName();
    return userAuthService.findByUsernameDto(username)
        .orElseThrow(() -> new EntityNotFoundException(EntityName.USER));
  }

  /**
   * Предоставляет сервис для работы с аутентификацией и авторизацией.
   *
   * @return объект {@link UserAuthService}.
   */
  @Override
  public BaseService<UserDto, UserForAuth> svc() {
    return userAuthService;
  }
}
