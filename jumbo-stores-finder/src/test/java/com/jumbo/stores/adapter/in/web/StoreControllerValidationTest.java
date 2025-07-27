package com.jumbo.stores.adapter.in.web;

import com.jumbo.stores.adapter.in.web.exception.ApiExceptionHandler;
import com.jumbo.stores.application.port.in.FindClosestStoresUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(StoreController.class)
@Import({ApiExceptionHandler.class, com.jumbo.stores.PostgresContainerConfig.class})
class StoreControllerValidationTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private FindClosestStoresUseCase findClosestStoresUseCase;

    @Test
    void whenLatitudeOutOfRange_thenBadRequest() {
        webTestClient.get()
                .uri("/stores?latitude=100&longitude=4.9")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void whenLongitudeOutOfRange_thenBadRequest() {
        webTestClient.get()
                .uri("/stores?latitude=52.0&longitude=200.0")
                .exchange()
                .expectStatus().isBadRequest();
    }
} 