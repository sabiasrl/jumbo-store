package com.jumbo.stores;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumbo.stores.model.Store;
import com.jumbo.stores.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

	private final StoreRepository storeRepository;

	@Override
	public void run(String... args) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		InputStream inputStream = DataLoader.class.getResourceAsStream("/stores.json");
		JsonNode root = mapper.readTree(inputStream);

		JsonNode storesNode = root.get("stores");
		List<Store> stores = mapper.convertValue(storesNode, new TypeReference<List<Store>>() {
		});
		storeRepository.saveAll(stores);
		log.info("{} stores loaded successfully into the database.", stores.size());
	}
}