package com.jumbo.stores.adapter.out.persistence;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface StoreR2dbcRepository extends ReactiveCrudRepository<StoreR2dbcEntity, Long> {

    @Query("SELECT * FROM store ORDER BY " +
           "SQRT(POWER(latitude - :latitude, 2) + POWER(longitude - :longitude, 2)) ASC " +
           "LIMIT :limit")
    Flux<StoreR2dbcEntity> findClosestStores(double longitude, double latitude, int limit);
} 