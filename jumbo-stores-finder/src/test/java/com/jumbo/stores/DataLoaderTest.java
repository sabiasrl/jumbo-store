package com.jumbo.stores;

import com.jumbo.stores.repository.StoreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Import(DataLoader.class)
public class DataLoaderTest {

	@Autowired
	private StoreRepository storeRepository;

	@Test
	public void run_shouldLoadStoresIntoDatabase() {
		assertEquals(587, storeRepository.count());
	}
}