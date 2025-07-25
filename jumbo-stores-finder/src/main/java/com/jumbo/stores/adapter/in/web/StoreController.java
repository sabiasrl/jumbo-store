package com.jumbo.stores.adapter.in.web;

import com.jumbo.stores.application.port.in.FindClosestStoresUseCase;
import com.jumbo.stores.adapter.in.web.dto.StoreDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import org.springframework.validation.annotation.Validated;

@RestController
@RequiredArgsConstructor
@Validated
public class StoreController {

    private final FindClosestStoresUseCase findClosestStoresUseCase;

    @GetMapping("/stores")
    public List<StoreDto> getClosestStores(
            @RequestParam @DecimalMin(value = "-90.0") @DecimalMax(value = "90.0") double latitude,
            @RequestParam @DecimalMin(value = "-180.0") @DecimalMax(value = "180.0") double longitude) {
        return findClosestStoresUseCase.findClosestStores(latitude, longitude);
    }
} 