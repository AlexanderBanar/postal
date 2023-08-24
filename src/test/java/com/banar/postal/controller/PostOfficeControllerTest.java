package com.banar.postal.controller;

import com.banar.postal.model.Delivery;
import com.banar.postal.model.DeliveryType;
import com.banar.postal.model.PostOffice;
import com.banar.postal.repository.DeliveryRepository;
import com.banar.postal.repository.PostOfficeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
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
import static com.banar.postal.controller.PostOfficeController.*;

@SpringBootTest
@AutoConfigureMockMvc
class PostOfficeControllerTest {

    private Long deliveryId;
    private Long postOfficeId;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private PostOfficeRepository postOfficeRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public void setUp() {
        Delivery delivery = Delivery.builder()
                .receiverAddress("address")
                .receiverIndex("123456")
                .receiverName("Ivan")
                .type(DeliveryType.LETTER)
                .build();

        delivery = deliveryRepository.saveAndFlush(delivery);
        deliveryId = delivery.getId();

        PostOffice postOffice = PostOffice.builder()
                .name("Post Office 1")
                .address("postOffice address 1")
                .index("post office 1 index")
                .build();

        postOffice = postOfficeRepository.saveAndFlush(postOffice);
        postOfficeId = postOffice.getId();
    }

    @Test
    public void registerAndOk() throws Exception {
        Delivery delivery = Delivery.builder()
                .receiverAddress("address")
                .receiverIndex("123456")
                .receiverName("Ivan")
                .type(DeliveryType.LETTER)
                .build();

        mockMvc.perform(post(REGISTER_NEW_DELIVERY)
                .content(objectMapper.writeValueAsString(delivery))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty());

    }

    @Test
    public void whenRegisterAndNok() throws Exception {
        Delivery deliveryHavingJustName = Delivery.builder()
                .receiverName("Ivan")
                .build();

        Delivery deliveryHavingJustIndex = Delivery.builder()
                .receiverIndex("123456")
                .build();

        Delivery deliveryHavingJustAddress = Delivery.builder()
                .receiverAddress("address")
                .build();

        registerNokTry(deliveryHavingJustName);
        registerNokTry(deliveryHavingJustIndex);
        registerNokTry(deliveryHavingJustAddress);
    }

    private void registerNokTry(Delivery delivery) throws Exception {
        mockMvc.perform(post(REGISTER_NEW_DELIVERY)
                .content(objectMapper.writeValueAsString(delivery))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        mvcResult.getResolvedException().getClass().equals(IllegalArgumentException.class));
    }

}