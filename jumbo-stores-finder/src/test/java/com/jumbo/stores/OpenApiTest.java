package com.jumbo.stores;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
@Import(com.jumbo.stores.PostgresContainerConfig.class)
public class OpenApiTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void openApiSpecification_shouldBeAvailable() {
        webTestClient.get()
                .uri("/v3/api-docs")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.openapi").isEqualTo("3.1.0")
                .jsonPath("$.info.title").isEqualTo("OpenAPI definition")
                .jsonPath("$.paths./stores").exists();
    }

    @Test
    public void swaggerUi_shouldBeAvailable() {
        webTestClient.get()
                .uri("/swagger-ui/index.html")
                .exchange()
                .expectStatus().isOk();
    }
} 