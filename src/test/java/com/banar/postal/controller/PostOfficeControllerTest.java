package com.banar.postal.controller;

import com.banar.postal.dto.DeliveryDTO;
import com.banar.postal.model.Delivery;
import com.banar.postal.model.DeliveryType;
import com.banar.postal.model.Gate;
import com.banar.postal.model.PostOffice;
import com.banar.postal.repository.DeliveryRepository;
import com.banar.postal.repository.GateRepository;
import com.banar.postal.repository.PostOfficeRepository;
import com.banar.postal.service.PostOfficeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static com.banar.postal.controller.PostOfficeController.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static com.banar.postal.dto.DeliveryDTO.convertToDelivery;

@SpringBootTest
@AutoConfigureMockMvc
class PostOfficeControllerTest {

    private static final String RECEIVER_ADDRESS = "address";
    private static final String RECEIVER_NAME = "Ivan";
    private static final String RECEIVER_INDEX = "index";
    private static final String POST_OFFICE_ADDRESS = "Post Office address";
    private static final String POST_OFFICE_NAME = "Post Office";
    private static final String POST_OFFICE_INDEX = "Post Office index";
    private static final Long WRONG_ID = 5L;

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

    @Autowired
    private PostOfficeService postOfficeService;

    @AfterEach
    public void resetDBs() {
        deliveryRepository.deleteAll();
        postOfficeRepository.deleteAll();
        gateRepository.deleteAll();
    }

    @Test
    public void registerAndOk() throws Exception {
        mockMvc.perform(post(REGISTER_NEW_DELIVERY)
                .content(objectMapper.writeValueAsString(getPreparedDeliveryDTO()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    public void whenRegisterAndNok() throws Exception {
        DeliveryDTO deliveryDTOHavingJustName = DeliveryDTO.builder()
                .receiverName(RECEIVER_NAME)
                .build();

        DeliveryDTO deliveryDTOHavingJustIndex = DeliveryDTO.builder()
                .receiverIndex(RECEIVER_INDEX)
                .build();

        DeliveryDTO deliveryDTOHavingJustAddress = DeliveryDTO.builder()
                .receiverAddress(RECEIVER_ADDRESS)
                .build();

        registerNokTry(deliveryDTOHavingJustName);
        registerNokTry(deliveryDTOHavingJustIndex);
        registerNokTry(deliveryDTOHavingJustAddress);
    }

    @Test
    public void whenArriveAndOk() throws Exception {
        Delivery delivery = deliveryRepository.saveAndFlush(convertToDelivery(getPreparedDeliveryDTO()));
        PostOffice postOffice = postOfficeRepository.saveAndFlush(getPreparedPostOffice());

        mockMvc.perform(post(ARRIVE_AT_POST_OFFICE, delivery.getId(), postOffice.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(Boolean.TRUE.toString()));

        Gate gate = gateRepository.findByDeliveryIdAndDepartureDateIsNull(delivery.getId());
        assertThat(gate.getArrivalDate(), is(notNullValue()));
        assertThat(gate.getId(), is(notNullValue()));
    }

    @Test
    public void whenArriveAndNok() throws Exception {
        mockMvc.perform(post(ARRIVE_AT_POST_OFFICE, WRONG_ID, WRONG_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        mvcResult.getResolvedException().getClass().equals(IllegalArgumentException.class));
    }

    @Test
    public void whenDepartAndOk() throws Exception {
        Delivery delivery = deliveryRepository.saveAndFlush(convertToDelivery(getPreparedDeliveryDTO()));
        PostOffice postOffice = postOfficeRepository.saveAndFlush(getPreparedPostOffice());

        mockMvc.perform(post(ARRIVE_AT_POST_OFFICE, delivery.getId(), postOffice.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(post(DEPART_FROM_POST_OFFICE, delivery.getId(), postOffice.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(Boolean.TRUE.toString()));

        Gate gate = gateRepository.findByDeliveryIdAndPostOfficeId(delivery.getId(), postOffice.getId());
        assertThat(gate.getArrivalDate(), is(notNullValue()));
        assertThat(gate.getDepartureDate(), is(notNullValue()));
    }

    @Test
    public void whenDepartAndNok() throws Exception {
        mockMvc.perform(post(DEPART_FROM_POST_OFFICE, WRONG_ID, WRONG_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        mvcResult.getResolvedException().getClass().equals(IllegalArgumentException.class));
    }

    @Test
    public void whenReceiveAndOk() throws Exception {
        Delivery delivery = deliveryRepository.saveAndFlush(convertToDelivery(getPreparedDeliveryDTO()));
        PostOffice postOffice = postOfficeRepository.saveAndFlush(getPreparedPostOffice());

        mockMvc.perform(post(ARRIVE_AT_POST_OFFICE, delivery.getId(), postOffice.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(post(DEPART_FROM_POST_OFFICE, delivery.getId(), postOffice.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(post(RECEIVE_BY_ADDRESSEE, delivery.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(Boolean.TRUE.toString()));

        Delivery receivedDelivery = deliveryRepository.findById(delivery.getId()).orElse(new Delivery());
        assertThat(receivedDelivery.isReceived(), is(true));
    }

    @Test
    public void whenReceiveAndNok() throws Exception {
        mockMvc.perform(post(RECEIVE_BY_ADDRESSEE, WRONG_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        mvcResult.getResolvedException().getClass().equals(IllegalArgumentException.class));
    }

    @Test
    public void getStatusAndOk() throws Exception {
        Delivery delivery = deliveryRepository.saveAndFlush(convertToDelivery(getPreparedDeliveryDTO()));
        PostOffice postOffice = postOfficeRepository.saveAndFlush(getPreparedPostOffice());

        mockMvc.perform(post(ARRIVE_AT_POST_OFFICE, delivery.getId(), postOffice.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(post(DEPART_FROM_POST_OFFICE, delivery.getId(), postOffice.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(post(RECEIVE_BY_ADDRESSEE, delivery.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        DeliveryDTO deliveryDTO = postOfficeService.getDeliveryDTO(delivery.getId());
        mockMvc.perform(get(GET_STATUS, delivery.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(deliveryDTO)));
    }

    @Test
    public void getStatusAndNok() throws Exception {
        mockMvc.perform(get(GET_STATUS, WRONG_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        mvcResult.getResolvedException().getClass().equals(IllegalArgumentException.class));
    }

    private void registerNokTry(DeliveryDTO deliveryDTO) throws Exception {
        mockMvc.perform(post(REGISTER_NEW_DELIVERY)
                .content(objectMapper.writeValueAsString(deliveryDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        mvcResult.getResolvedException().getClass().equals(ConstraintViolationException.class));
    }

    private static DeliveryDTO getPreparedDeliveryDTO() {
        return DeliveryDTO.builder().receiverAddress(RECEIVER_ADDRESS).receiverIndex(RECEIVER_INDEX).receiverName(RECEIVER_NAME).type(DeliveryType.LETTER)
                .build();
    }

    private static PostOffice getPreparedPostOffice() {
        return PostOffice.builder().name(POST_OFFICE_NAME).address(POST_OFFICE_ADDRESS).index(POST_OFFICE_INDEX)
                .build();
    }

}