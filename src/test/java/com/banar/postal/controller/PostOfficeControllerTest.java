package com.banar.postal.controller;

import com.banar.postal.model.Delivery;
import com.banar.postal.model.DeliveryType;
import com.banar.postal.repository.DeliveryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class PostOfficeControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    public void resetDb() {
        deliveryRepository.deleteAll();
    }

    @Test
    public void registerAndOk() throws Exception {
        Delivery delivery = Delivery.builder()
                .receiverAddress("receiver")
                .receiverIndex("123456")
                .receiverName("Ivan")
                .type(DeliveryType.LETTER)
                .build();

        mockMvc.perform(post("/register")
                .content(objectMapper.writeValueAsString(delivery))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty());

    }

}