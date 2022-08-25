package com.juan.learn.testcontainers.base;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

public abstract class AbstractContainers {

    private static final MySQLContainer MYSQL;

    static {
        MYSQL = new MySQLContainer(DockerImageName.parse("mysql:latest"));
        MYSQL.withDatabaseName("todo_db");
        MYSQL.withUsername("user");
        MYSQL.withPassword("12345");
        MYSQL.withExposedPorts(Integer.valueOf("3306"));
        MYSQL.withInitScript("schema.sql");
        MYSQL.start();
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MYSQL::getJdbcUrl);
        registry.add("spring.datasource.username", MYSQL::getUsername);
        registry.add("spring.datasource.password", MYSQL::getPassword);
    }
}
