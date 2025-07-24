package com.jumbo.stores.service;

import com.jumbo.stores.dto.StoreDto;
import com.jumbo.stores.model.Store;
import com.jumbo.stores.repository.StoreRepository;
import com.jumbo.stores.utils.DistanceCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreService {

    private final StoreRepository storeRepository;

    public List<StoreDto> findClosestStores(double latitude, double longitude) {
        List<Store> stores = storeRepository.findClosestStoresPostgis(longitude, latitude, 5);
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