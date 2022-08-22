package com.juan.learn.testcontainers.base;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

public abstract class AbstractIntegrationTest {

    private static final MySQLContainer mysqldb;

    static {
        mysqldb = new MySQLContainer(DockerImageName.parse("mysql:latest"));
        mysqldb.start();
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqldb::getJdbcUrl);
        registry.add("spring.datasource.username", mysqldb::getUsername);
        registry.add("spring.datasource.password", mysqldb::getPassword);
    }
}