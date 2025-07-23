package com.jumbo.stores.controller;

import com.jumbo.stores.model.StoreDto;
import com.jumbo.stores.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StoreController {

	private final StoreService storeService;

	@GetMapping("/stores")
	public List<StoreDto> getClosestStores(@RequestParam double latitude, @RequestParam double longitude)
			throws IOException {
		return storeService.findClosestStores(latitude, longitude);
	}
}