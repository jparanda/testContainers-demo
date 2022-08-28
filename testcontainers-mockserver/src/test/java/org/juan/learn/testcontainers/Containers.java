package org.juan.learn.testcontainers;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;


public class Containers {

    public static final MySQLContainer MYSQL;
    public static final MockServerContainer EMAIL_SERVICE;

    static {
        MYSQL = new MySQLContainer(DockerImageName.parse("mysql:5.7"));
        MYSQL.withDatabaseName("customer_db");
        MYSQL.withUsername("user");
        MYSQL.withPassword("12345");
        MYSQL.withExposedPorts(Integer.valueOf("3306"));
        MYSQL.withInitScript("schema.sql");
        MYSQL.start();

        EMAIL_SERVICE = new MockServerContainer(DockerImageName.parse("mockserver/mockserver:5.13.2"));
        EMAIL_SERVICE.start();

    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MYSQL::getJdbcUrl);
        registry.add("spring.datasource.username", MYSQL::getUsername);
        registry.add("spring.datasource.password", MYSQL::getPassword);

        registry.add("services.email.base-url", EMAIL_SERVICE::getEndpoint);
    }


}
