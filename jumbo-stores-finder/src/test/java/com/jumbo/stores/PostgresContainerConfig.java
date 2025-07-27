package com.jumbo.stores;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@TestConfiguration
@Testcontainers
public class PostgresContainerConfig {

    @Container
    static final PostgreSQLContainer<?> postgres;

    static {
        DockerImageName postgisImage = DockerImageName.parse("postgis/postgis:16-3.4")
                .asCompatibleSubstituteFor("postgres");
        postgres = new PostgreSQLContainer<>(postgisImage)
                .withDatabaseName("jumbo")
                .withUsername("jumbo")
                .withPassword("jumbo");
        postgres.start();
        System.setProperty("spring.datasource.url", postgres.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgres.getUsername());
        System.setProperty("spring.datasource.password", postgres.getPassword());
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        // Convert JDBC URL to R2DBC URL format
        String jdbcUrl = postgres.getJdbcUrl();
        // Extract host and port from JDBC URL: jdbc:postgresql://localhost:port/database
        String host = postgres.getHost();
        int port = postgres.getMappedPort(5432);
        String database = postgres.getDatabaseName();
        String r2dbcUrl = String.format("r2dbc:postgresql://%s:%d/%s", host, port, database);
        
        registry.add("spring.r2dbc.url", () -> r2dbcUrl);
        registry.add("spring.r2dbc.username", postgres::getUsername);
        registry.add("spring.r2dbc.password", postgres::getPassword);
        registry.add("spring.liquibase.enabled", () -> false);
    }
} 