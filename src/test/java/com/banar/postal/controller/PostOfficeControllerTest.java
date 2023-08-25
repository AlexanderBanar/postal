package com.banar.postal.controller;

import com.banar.postal.model.Delivery;
import com.banar.postal.model.DeliveryType;
import com.banar.postal.model.Gate;
import com.banar.postal.model.PostOffice;
import com.banar.postal.repository.DeliveryRepository;
import com.banar.postal.repository.GateRepository;
import com.banar.postal.repository.PostOfficeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static com.banar.postal.controller.PostOfficeController.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PostOfficeControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private PostOfficeRepository postOfficeRepository;

    @Autowired
    private GateRepository gateRepository;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    public void resetDBs() {
        deliveryRepository.deleteAll();
        postOfficeRepository.deleteAll();
        gateRepository.deleteAll();
    }

    @Test
    public void registerAndOk() throws Exception {
        mockMvc.perform(post(REGISTER_NEW_DELIVERY)
                .content(objectMapper.writeValueAsString(getPreparedDelivery()))
                .contentType(MediaType.APPLICATION_JSON))
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

    @Test
    public void whenArriveAndOk() throws Exception {
        Delivery delivery = deliveryRepository.saveAndFlush(getPreparedDelivery());
        PostOffice postOffice = postOfficeRepository.saveAndFlush(getPreparedPostOffice1());

        mockMvc.perform(post(ARRIVE_AT_POST_OFFICE, delivery.getId(), postOffice.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        Gate gate = gateRepository.findByDeliveryIdAndDepartureDateIsNull(delivery.getId());
        assertThat(gate.getId(), is(notNullValue()));
    }

    @Test
    public void whenArriveAndNok() throws Exception {
        mockMvc.perform(post(ARRIVE_AT_POST_OFFICE, 5, 7)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        mvcResult.getResolvedException().getClass().equals(IllegalArgumentException.class));
    }

    private void registerNokTry(Delivery delivery) throws Exception {
        mockMvc.perform(post(REGISTER_NEW_DELIVERY)
                .content(objectMapper.writeValueAsString(delivery))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        mvcResult.getResolvedException().getClass().equals(IllegalArgumentException.class));
    }

    private static Delivery getPreparedDelivery() {
        return Delivery.builder().receiverAddress("address").receiverIndex("123456").receiverName("Ivan").type(DeliveryType.LETTER)
                .build();
    }

    private static PostOffice getPreparedPostOffice1() {
        return PostOffice.builder().name("Post Office 1").address("postOffice address 1").index("post office 1 index")
                .build();
    }

    private static PostOffice getPreparedPostOffice2() {
        return PostOffice.builder().name("Post Office 2").address("postOffice address 2").index("post office 2 index")
                .build();
    }

}