package com.jumbo.stores.application.service;

import com.jumbo.stores.application.port.in.FindClosestStoresUseCase;
import com.jumbo.stores.application.port.out.StoreRepository;
import com.jumbo.stores.domain.model.Store;
import com.jumbo.stores.domain.service.DistanceCalculator;
import com.jumbo.stores.adapter.in.web.dto.StoreDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreService implements FindClosestStoresUseCase {

    private final StoreRepository storeRepository;

    @Override
    public List<StoreDto> findClosestStores(double latitude, double longitude) {
        List<Store> stores = storeRepository.findClosestStores(longitude, latitude, 5);
        return stores.stream()
                .map(store -> {
                    double distance = DistanceCalculator.calculate(latitude, longitude, 
                                                                  store.getLatitude(), store.getLongitude());
                    return new StoreDto(store.getAddressName(), store.getLatitude(), 
                                      store.getLongitude(), distance);
                })
                .collect(Collectors.toList());
    }
} 