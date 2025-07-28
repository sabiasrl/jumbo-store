package com.jumbo.stores.integrationtests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({com.jumbo.stores.PostgresContainerConfig.class})
class StoreControllerIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    @Disabled
    void getClosestStores_shouldReturn5Stores() {
        given()
                .when()
                .get("/stores?latitude=51.58751&longitude=4.753484") // Coordinates near Breda Cypresstraat store
                .then()
                .log().ifError() // Log response body if there's an error
                .statusCode(200)
                .body("$", hasSize(5));
    }
} 