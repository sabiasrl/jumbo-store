package com.jumbo.stores.adapter.in.web;

import com.jumbo.stores.adapter.in.web.dto.StoreDto;
import com.jumbo.stores.application.port.in.FindClosestStoresUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.BDDMockito.given;

@WebFluxTest(StoreController.class)
@Import(com.jumbo.stores.PostgresContainerConfig.class)
public class StoreControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private FindClosestStoresUseCase findClosestStoresUseCase;

    @Test
    public void getClosestStores_shouldReturnStores() {
        StoreDto storeDto = new StoreDto("Test Store", 52.3676, 4.9041, 0.0);

        given(findClosestStoresUseCase.findClosestStores(52.3676, 4.9041)).willReturn(Flux.just(storeDto));

        webTestClient.get()
                .uri("/stores?latitude=52.3676&longitude=4.9041")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(StoreDto.class)
                .hasSize(1)
                .contains(storeDto);
    }
} 