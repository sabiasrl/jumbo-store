package com.jumbo.stores.integrationtests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({com.jumbo.stores.PostgresContainerConfig.class, com.jumbo.stores.TestDataConfig.class})
class StoreControllerIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void getClosestStores_shouldReturn5Stores() {
        given()
                .when()
                .get("/stores?latitude=51.6167&longitude=5.5486")
                .then()
                .statusCode(200)
                .body("$", hasSize(5));
    }
} 