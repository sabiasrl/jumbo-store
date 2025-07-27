package com.jumbo.stores.application.port.out;

import com.jumbo.stores.domain.model.Store;
import reactor.core.publisher.Flux;

public interface StoreRepository {
    Flux<Store> findClosestStores(double longitude, double latitude, int limit);
} 