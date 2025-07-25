package com.jumbo.stores.application.port.out;

import com.jumbo.stores.domain.model.Store;
import java.util.List;

public interface StoreRepository {
    List<Store> findClosestStores(double longitude, double latitude, int limit);
} 