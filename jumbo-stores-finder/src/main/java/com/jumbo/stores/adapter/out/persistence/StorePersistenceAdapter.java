package com.jumbo.stores.adapter.out.persistence;

import com.jumbo.stores.application.port.out.StoreRepository;
import com.jumbo.stores.domain.model.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StorePersistenceAdapter implements StoreRepository {

    private final StoreJpaRepository storeJpaRepository;

    @Override
    public List<Store> findClosestStores(double longitude, double latitude, int limit) {
        List<StoreJpaEntity> entities = storeJpaRepository.findClosestStoresPostgis(longitude, latitude, limit);
        return entities.stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    private Store mapToDomain(StoreJpaEntity entity) {
        return new Store(
                entity.getId(),
                entity.getAddressName(),
                entity.getLocation().getY(), // latitude
                entity.getLocation().getX()  // longitude
        );
    }
} 