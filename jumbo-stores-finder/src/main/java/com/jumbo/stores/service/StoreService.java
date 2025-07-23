package com.jumbo.stores.service;

import com.jumbo.stores.dto.StoreDto;
import com.jumbo.stores.model.Store;
import com.jumbo.stores.repository.StoreRepository;
import com.jumbo.stores.utils.DistanceCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {

	private final StoreRepository storeRepository;

	public List<StoreDto> findClosestStores(double latitude, double longitude) {
		List<Store> allStores = storeRepository.findAll();

		return allStores.stream().map(store -> {
			double distance = DistanceCalculator.calculate(latitude, longitude, store.getLatitude(),
					store.getLongitude());
			return new StoreDto(store.getAddressName(), store.getLatitude(), store.getLongitude(), distance);
		}).sorted(Comparator.comparingDouble(StoreDto::distance)).limit(5).collect(Collectors.toList());
	}
}