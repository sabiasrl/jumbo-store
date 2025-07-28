package com.jumbo.stores.application.service;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jumbo.stores.application.port.out.StoreRepository;
import com.jumbo.stores.domain.model.Store;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StoreService storeService;

    @Test
    void findClosestStores_shouldReturnClosestStores() {
        // Arrange
        GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);
        
        Store store1 = new Store(1L, "Store 1", factory.createPoint(new org.locationtech.jts.geom.Coordinate(5.0, 52.0)), 0.0);
        Store store2 = new Store(2L, "Store 2", factory.createPoint(new org.locationtech.jts.geom.Coordinate(5.1, 52.1)), 0.0);
        Store store3 = new Store(3L, "Store 3", factory.createPoint(new org.locationtech.jts.geom.Coordinate(5.2, 52.2)), 0.0);
        Store store4 = new Store(4L, "Store 4", factory.createPoint(new org.locationtech.jts.geom.Coordinate(5.3, 52.3)), 0.0);
        Store store5 = new Store(5L, "Store 5", factory.createPoint(new org.locationtech.jts.geom.Coordinate(5.4, 52.4)), 0.0);

        // Mock repository call
        when(storeRepository.findClosestStores(5.0, 52.0, 5))
            .thenReturn(Flux.just(store1, store2, store3, store4, store5));

        // Act & Assert
        StepVerifier.create(storeService.findClosestStores(52.0, 5.0))
            .expectNextCount(5)
            .verifyComplete();
    }
} 