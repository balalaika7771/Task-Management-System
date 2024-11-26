package org.example.task_management_system;

import org.example.task_management_system.core.dto.UserDto;
import org.example.task_management_system.security.request.AuthRequest;
import org.example.task_management_system.security.request.RegisterRequest;
import org.example.task_management_system.security.response.AuthResponse;
import org.example.task_management_system.task_management.dto.CommentDto;
import org.example.task_management_system.task_management.dto.TaskDto;
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
class TaskManagementSystemTest {

  @Container
  private static final PostgreSQLContainer<?> postgresContainer =
      new PostgreSQLContainer<>("postgres:15")
          .withDatabaseName("testdb")
          .withUsername("user")
          .withPassword("password");

  @Autowired
  private WebTestClient webTestClient;
  private RegisterRequest registerRequest;
  private AuthRequest userAuthRequest;
  private AuthRequest adminAuthRequest;

  @DynamicPropertySource
  static void postgresProperties(org.springframework.test.context.DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
    registry.add("spring.datasource.username", postgresContainer::getUsername);
    registry.add("spring.datasource.password", postgresContainer::getPassword);
  }

  @BeforeEach
  void setUp() {
    registerRequest = new RegisterRequest("testuser", "testpassword", "testuser@example.com");
    userAuthRequest = new AuthRequest("testuser", "testpassword");
    adminAuthRequest = new AuthRequest("admin", "admin");
  }

  @Test
  void taskManagementFlow() {
    // Step 1: Register a new user
    webTestClient.post()
        .uri("/api/auth/register")
        .contentType(APPLICATION_JSON)
        .bodyValue(registerRequest)
        .exchange()
        .expectStatus().isOk();

    // Step 2: User logs in and gets a token
    AuthResponse userAuthResponse = webTestClient.post()
        .uri("/api/auth/login")
        .contentType(APPLICATION_JSON)
        .bodyValue(userAuthRequest)
        .exchange()
        .expectStatus().isOk()
        .expectBody(AuthResponse.class)
        .returnResult()
        .getResponseBody();

    assertThat(userAuthResponse).isNotNull();
    assertThat(userAuthResponse.getToken()).isNotBlank();

    // Step 3: Admin logs in and gets a token
    AuthResponse adminAuthResponse = webTestClient.post()
        .uri("/api/auth/login")
        .contentType(APPLICATION_JSON)
        .bodyValue(adminAuthRequest)
        .exchange()
        .expectStatus().isOk()
        .expectBody(AuthResponse.class)
        .returnResult()
        .getResponseBody();

    assertThat(adminAuthResponse).isNotNull();
    assertThat(adminAuthResponse.getToken()).isNotBlank();

    // Step 4: User retrieves the registered user's ID
    UserDto registeredUser = webTestClient.get()
        .uri("/api/auth/who_am_i")
        .header("Authorization", "Bearer " + userAuthResponse.getToken())  // Передаём токен в заголовке
        .exchange()
        .expectStatus().isOk()  // Ожидаем, что ответ будет успешным (200)
        .expectBody(UserDto.class)  // Маппим тело как UserDto
        .returnResult()
        .getResponseBody();

    assertThat(registeredUser).isNotNull();  // Проверяем, что пользователь найден

    // Step 5: Admin creates a task and assigns the new user as executor
    TaskDto taskRequest = new TaskDto(null, "Test Task", "This is a test task.", registeredUser.getId(), "PENDING", "MEDIUM");
    TaskDto createdTask = webTestClient.post()
        .uri("/api/Task/save")
        .header("Authorization", "Bearer " + adminAuthResponse.getToken())
        .contentType(APPLICATION_JSON)
        .bodyValue(taskRequest)
        .exchange()
        .expectStatus().isOk()
        .expectBody(TaskDto.class)
        .returnResult()
        .getResponseBody();

    assertThat(createdTask).isNotNull();
    assertThat(createdTask.getTitle()).isEqualTo("Test Task");
    assertThat(createdTask.getBody()).isEqualTo("This is a test task.");
    assertThat(createdTask.getExecutorId()).isEqualTo(registeredUser.getId());


    // Step 6: User comments on their task
    CommentDto commentRequest = new CommentDto(null, createdTask.getId(), "This is a comment on the task.");
    CommentDto createdComment = webTestClient.post()
        .uri("/api/Comment/save-own")
        .header("Authorization", "Bearer " + userAuthResponse.getToken())
        .contentType(APPLICATION_JSON)
        .bodyValue(commentRequest)
        .exchange()
        .expectStatus().isOk()
        .expectBody(CommentDto.class)
        .returnResult()
        .getResponseBody();

    assertThat(createdComment).isNotNull();
    assertThat(createdComment.getBody()).isEqualTo("This is a comment on the task.");
    assertThat(createdComment.getTaskId()).isEqualTo(createdTask.getId());
  }

}
