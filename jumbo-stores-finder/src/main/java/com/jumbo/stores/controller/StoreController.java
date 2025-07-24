package com.jumbo.stores.controller;

import com.jumbo.stores.dto.StoreDto;
import com.jumbo.stores.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import org.springframework.validation.annotation.Validated;

@RestController
@RequiredArgsConstructor
@Validated
public class StoreController {

	private final StoreService storeService;

	@GetMapping("/stores")
	public List<StoreDto> getClosestStores(
			@RequestParam @DecimalMin(value = "-90.0") @DecimalMax(value = "90.0") double latitude,
			@RequestParam @DecimalMin(value = "-180.0") @DecimalMax(value = "180.0") double longitude)
			throws IOException {
		return storeService.findClosestStores(latitude, longitude);
	}
}