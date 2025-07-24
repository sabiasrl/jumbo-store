package com.jumbo.stores;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumbo.stores.model.Store;
import com.jumbo.stores.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final StoreRepository storeRepository;

    @Override
    public void run(String... args) throws Exception {
        if (storeRepository.count() > 0) {
            log.info("Stores already loaded, skipping data loading");
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = DataLoader.class.getResourceAsStream("/stores.json");
        JsonNode root = mapper.readTree(inputStream);
        JsonNode storesNode = root.get("stores");

        List<Store> stores = new ArrayList<>();
        GeometryFactory geometryFactory = new GeometryFactory();

        storesNode.forEach(node -> {
            String addressName = node.get("addressName").asText();
            double latitude = node.get("latitude").asDouble();
            double longitude = node.get("longitude").asDouble();

            try {
                // Try to create Point geometry (works with PostGIS)
                Point location = geometryFactory.createPoint(new Coordinate(longitude, latitude));
                location.setSRID(4326);
                Store store = new Store(null, addressName, location);
                stores.add(store);
            } catch (Exception e) {
                // Fallback - use constructor that creates Point internally
                Store store = new Store(null, addressName, latitude, longitude);
                stores.add(store);
            }
        });

        storeRepository.saveAll(stores);
        log.info("{} stores loaded successfully into the database.", stores.size());
    }
} 