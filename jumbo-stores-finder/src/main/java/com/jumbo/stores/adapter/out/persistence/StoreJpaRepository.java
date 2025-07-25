package com.jumbo.stores.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreJpaRepository extends JpaRepository<StoreJpaEntity, Long> {

    @Query(value = "SELECT * FROM store ORDER BY ST_DistanceSphere(location, ST_MakePoint(?1, ?2)) ASC LIMIT ?3", 
           nativeQuery = true)
    List<StoreJpaEntity> findClosestStoresPostgis(@Param("longitude") double longitude, 
                                                  @Param("latitude") double latitude, 
                                                  @Param("limit") int limit);
} 