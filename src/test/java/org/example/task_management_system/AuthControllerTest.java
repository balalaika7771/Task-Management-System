package org.example.task_management_system;

import org.example.task_management_system.core.dto.UserDto;
import org.example.task_management_system.security.request.AuthRequest;
import org.example.task_management_system.security.request.RegisterRequest;
import org.example.task_management_system.security.response.AuthResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Testcontainers
class AuthControllerTest {

  @Container
  private static final PostgreSQLContainer<?> postgresContainer =
      new PostgreSQLContainer<>("postgres:15")
          .withDatabaseName("testdb")
          .withUsername("user")
          .withPassword("password");

  @Autowired
  private WebTestClient webTestClient;

  @DynamicPropertySource
  static void postgresProperties(org.springframework.test.context.DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
    registry.add("spring.datasource.username", postgresContainer::getUsername);
    registry.add("spring.datasource.password", postgresContainer::getPassword);
  }

  private RegisterRequest registerRequest;
  private AuthRequest authRequest;

  @BeforeEach
  void setUp() {
    registerRequest = new RegisterRequest("testuser", "testpassword", "testuser@example.com");
    authRequest = new AuthRequest("testuser", "testpassword");
  }

  @Test
  void registerAndLoginUser() {
    // Регистрируем пользователя
    webTestClient.post()
        .uri("/api/auth/register")
        .contentType(APPLICATION_JSON)
        .bodyValue(registerRequest)
        .exchange()
        .expectStatus().isOk();

    // Авторизуемся и проверяем токен
    AuthResponse authResponse = webTestClient.post()
        .uri("/api/auth/login")
        .contentType(APPLICATION_JSON)
        .bodyValue(authRequest)
        .exchange()
        .expectStatus().isOk()
        .expectBody(AuthResponse.class)
        .returnResult()
        .getResponseBody();

    assertThat(authResponse).isNotNull();
    assertThat(authResponse.getUsername()).isEqualTo("testuser");
    assertThat(authResponse.getToken()).isNotBlank();

    // Используем полученный токен для вызова who_am_i
    webTestClient.get()
        .uri("/api/auth/who_am_i")
        .header("Authorization", "Bearer " + authResponse.getToken())
        .exchange()
        .expectStatus().isOk()
        .expectBody(UserDto.class)
        .value(userDto -> assertThat(userDto.getUsername()).isEqualTo("testuser"));
  }
}
