package com.juan.learn.testcontainers.todo.controller;


import com.juan.learn.testcontainers.TestContainersDbApplication;
import com.juan.learn.testcontainers.base.AbstractContainers;
import com.juan.learn.testcontainers.todo.model.Todo;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = TestContainersDbApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class TodoControllerContainersIntegrationFirstTest {

    @Container
    private static final MySQLContainer MYSQL = (MySQLContainer) new MySQLContainer(DockerImageName.parse("mysql:5.7"))
            .withDatabaseName("todo_db")
            .withUsername("user")
            .withPassword("12345")
            .withInitScript("schema.sql");

    @BeforeAll
    private static void initDatabaseProperties() {
        System.setProperty("spring.datasource.url", MYSQL.getJdbcUrl());
        System.setProperty("spring.datasource.username", MYSQL.getUsername());
        System.setProperty("spring.datasource.password", MYSQL.getPassword());
    }

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void getAllTest() {
        // Act
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Todo[]> todosRespEntity = this.testRestTemplate
                .exchange("http://localhost:" + port + "/todo-service/api/todos", HttpMethod.GET, entity, Todo[].class);

        // Assert
        Assertions.assertThat(todosRespEntity.getBody().length).isEqualTo(3);
        Assertions.assertThat(todosRespEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void getTest() {
        // Arrange
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Act
        ResponseEntity<Todo> todoRespEntity = this.testRestTemplate
                .exchange("http://localhost:" + port + "/todo-service/api/todos/{id}", HttpMethod.GET, entity, Todo.class, 1);

        // Assert
        Assertions.assertThat(todoRespEntity.getStatusCodeValue()).isEqualTo(200);
        Assertions.assertThat(todoRespEntity.getBody().getOwner()).isEqualTo("Juan");
    }

    @Test
    void createTest() {
        // Arrange
        Todo newTodo = Todo.builder()
                .title("Review Calandar")
                .note("Review calendar to check futures meetings")
                .owner("Cata").build();

        HttpEntity<Todo> entity = new HttpEntity<>(newTodo);

        // Act
        ResponseEntity<Todo> todoRespEntity = this.testRestTemplate
                .exchange("http://localhost:" + port + "/todo-service/api/todos", HttpMethod.POST, entity, Todo.class);

        // Assert
        Assertions.assertThat(todoRespEntity.getStatusCodeValue()).isEqualTo(201);
        Assertions.assertThat(todoRespEntity.getBody().getOwner()).isEqualTo("Cata");
    }

    @Test
    void deleteTest() {
        // Arrange
        Map<String, String> params = new HashMap<>();
        params.put("id", "1");
        // Act
        this.testRestTemplate
                .delete("http://localhost:" + port + "/todo-service/api/todos/{id}", params);

        Todo[] todos = this.testRestTemplate
                .getForObject("http://localhost:" + port + "/todo-service/api/todos", Todo[].class);

        // Assert
        Assertions.assertThat(todos.length).isEqualTo(3);
    }
}