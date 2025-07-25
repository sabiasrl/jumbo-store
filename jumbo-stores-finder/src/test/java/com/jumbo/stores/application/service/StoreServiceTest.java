package com.jumbo.stores.application.service;

import com.jumbo.stores.adapter.in.web.dto.StoreDto;
import com.jumbo.stores.application.port.out.StoreRepository;
import com.jumbo.stores.domain.model.Store;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StoreService storeService;

    @Test
    void findClosestStores_shouldReturnClosestStores() {
        // Arrange
        Store store1 = new Store(1L, "Store 1", 52.0, 5.0);
        Store store2 = new Store(2L, "Store 2", 52.1, 5.1);
        Store store3 = new Store(3L, "Store 3", 52.2, 5.2);
        Store store4 = new Store(4L, "Store 4", 52.3, 5.3);
        Store store5 = new Store(5L, "Store 5", 52.4, 5.4);

        // Mock repository call
        when(storeRepository.findClosestStores(5.0, 52.0, 5))
            .thenReturn(Arrays.asList(store1, store2, store3, store4, store5));

        // Act
        List<StoreDto> closestStores = storeService.findClosestStores(52.0, 5.0);

        // Assert
        assertEquals(5, closestStores.size());
        assertEquals("Store 1", closestStores.get(0).addressName());
        // The first store should be closest (distance 0) since it's at the same coordinates
    }
} 