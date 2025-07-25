package com.jumbo.stores.controller;

import com.jumbo.stores.exception.ApiExceptionHandler;
import com.jumbo.stores.service.StoreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StoreController.class)
@Import({ApiExceptionHandler.class, com.jumbo.stores.PostgresContainerConfig.class})
class StoreControllerValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StoreService storeService;

    @Test
    void whenLatitudeOutOfRange_thenBadRequest() throws Exception {
        mockMvc.perform(get("/stores?latitude=100&longitude=4.9"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenLongitudeOutOfRange_thenBadRequest() throws Exception {
        mockMvc.perform(get("/stores?latitude=52.0&longitude=200.0"))
                .andExpect(status().isBadRequest());
    }
} 