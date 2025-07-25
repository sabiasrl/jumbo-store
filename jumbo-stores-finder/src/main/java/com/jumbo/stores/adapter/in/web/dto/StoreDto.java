package com.jumbo.stores.adapter.in.web.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

public record StoreDto(
        @NotBlank String addressName,
        @DecimalMin(value = "-90.0") @DecimalMax(value = "90.0") double latitude,
        @DecimalMin(value = "-180.0") @DecimalMax(value = "180.0") double longitude,
        double distance) {
} 