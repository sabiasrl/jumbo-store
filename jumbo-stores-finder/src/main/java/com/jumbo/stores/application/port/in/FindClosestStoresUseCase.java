package com.jumbo.stores.application.port.in;

import com.jumbo.stores.adapter.in.web.dto.StoreDto;
import reactor.core.publisher.Flux;

public interface FindClosestStoresUseCase {
    Flux<StoreDto> findClosestStores(double latitude, double longitude);
} 