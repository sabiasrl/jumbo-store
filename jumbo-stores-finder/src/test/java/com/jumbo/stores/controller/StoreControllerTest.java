package com.jumbo.stores.controller;

import com.jumbo.stores.dto.StoreDto;
import com.jumbo.stores.repository.StoreRepository;
import com.jumbo.stores.service.StoreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StoreController.class)
@Import(com.jumbo.stores.PostgresContainerConfig.class)
public class StoreControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private StoreService storeService;

	@Test
	public void getClosestStores_shouldReturnStores() throws Exception {
		StoreDto storeDto = new StoreDto("Test Store", 52.3676, 4.9041, 0.0);

		given(storeService.findClosestStores(52.3676, 4.9041)).willReturn(Collections.singletonList(storeDto));

		mockMvc.perform(get("/stores?latitude=52.3676&longitude=4.9041")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].addressName").value("Test Store"));
	}
}