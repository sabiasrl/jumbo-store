package com.jumbo.stores.application.port.in;

import com.jumbo.stores.adapter.in.web.dto.StoreDto;
import java.util.List;

public interface FindClosestStoresUseCase {
    List<StoreDto> findClosestStores(double latitude, double longitude);
} 