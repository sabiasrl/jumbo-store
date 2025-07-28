package com.jumbo.stores;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.jumbo.stores.application.port.out.StoreRepository;

import reactor.core.publisher.Flux;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude = {R2dbcAutoConfiguration.class})
public class OpenApiTest {

    @LocalServerPort
    private int port;

    @MockBean
    private StoreRepository storeRepository;

    @Test
    public void openApiSpecification_shouldBeAvailable() {
        // Mock the repository to return empty results
        when(storeRepository.findClosestStores(anyDouble(), anyDouble(), anyInt()))
                .thenReturn(Flux.empty());

        given()
                .port(port)
                .when()
                .get("/v3/api-docs")
                .then()
                .statusCode(200)
                .body("openapi", org.hamcrest.Matchers.equalTo("3.1.0"))
                .body("info.title", org.hamcrest.Matchers.equalTo("OpenAPI definition"));
    }

    @Test
    public void swaggerUi_shouldBeAvailable() {
        // Mock the repository to return empty results
        when(storeRepository.findClosestStores(anyDouble(), anyDouble(), anyInt()))
                .thenReturn(Flux.empty());

        given()
                .port(port)
                .when()
                .get("/swagger-ui/index.html")
                .then()
                .statusCode(200);
    }
} 