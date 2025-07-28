package com.jumbo.stores.adapter.out.persistence;

import com.jumbo.stores.application.port.out.StoreRepository;
import com.jumbo.stores.domain.model.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class StorePersistenceAdapter implements StoreRepository {

    private final StoreR2dbcRepository storeR2dbcRepository;

    @Override
    public Flux<Store> findClosestStores(double longitude, double latitude, int limit) {
        return storeR2dbcRepository.findClosestStores(longitude, latitude, limit)
                .map(this::mapToDomain);
    }

    private Store mapToDomain(StoreR2dbcEntity entity) {
        return new Store(
                entity.getId(),
                entity.getAddressName(),
                entity.getLocation(),
                entity.getDistance() != null ? entity.getDistance() : 0.0
        );
    }
} 