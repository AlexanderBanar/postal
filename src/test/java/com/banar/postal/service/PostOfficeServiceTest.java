package com.banar.postal.service;

import com.banar.postal.dto.DeliveryDTO;
import com.banar.postal.model.Delivery;
import com.banar.postal.model.Gate;
import com.banar.postal.model.PostOffice;
import com.banar.postal.repository.DeliveryRepository;
import com.banar.postal.repository.GateRepository;
import com.banar.postal.repository.PostOfficeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
class PostOfficeServiceTest {

    private static final Long MOCK_OK_ID = 1L;
    private static final Long MOCK_NOK_ID = 2L;

    @InjectMocks
    private PostOfficeService postOfficeService;

    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private PostOfficeRepository postOfficeRepository;

    @Mock
    private GateRepository gateRepository;

    @Test
    public void whenProcessArrival() {
        Mockito.when(deliveryRepository.findById(MOCK_OK_ID)).thenReturn(Optional.of(getMockDelivery()));
        Mockito.when(postOfficeRepository.findById(MOCK_OK_ID)).thenReturn(Optional.of(getMockPostOffice()));
        Mockito.when(gateRepository.saveAndFlush(Mockito.any())).thenReturn(getMockGate());

        assertThat(postOfficeService.processArrival(MOCK_OK_ID, MOCK_OK_ID), is(true));
        assertThat(postOfficeService.processArrival(MOCK_NOK_ID, MOCK_NOK_ID), is(false));
    }

    @Test
    public void whenProcessDeparture() {
        Mockito.when(gateRepository.findByDeliveryIdAndPostOfficeId(MOCK_OK_ID, MOCK_OK_ID)).thenReturn(getMockGate());
        Mockito.when(gateRepository.saveAndFlush(Mockito.any())).thenReturn(getMockGate());

        assertThat(postOfficeService.processDeparture(MOCK_OK_ID, MOCK_OK_ID), is(true));
        assertThat(postOfficeService.processDeparture(MOCK_NOK_ID, MOCK_NOK_ID), is(false));
    }

    @Test
    public void whenProcessReceipt() {
        Mockito.when(deliveryRepository.findById(MOCK_OK_ID)).thenReturn(Optional.of(getMockDelivery()));
        Mockito.when(gateRepository.findByDeliveryIdAndDepartureDateIsNull(MOCK_OK_ID)).thenReturn(getMockGate());
        Mockito.when(deliveryRepository.saveAndFlush(Mockito.any())).thenReturn(getMockDelivery());

        assertThat(postOfficeService.processReceipt(MOCK_OK_ID), is(true));
        assertThat(postOfficeService.processReceipt(MOCK_NOK_ID), is(false));
    }

    @Test
    public void whenGetDeliveryDTO() {
        Mockito.when(deliveryRepository.findById(MOCK_OK_ID)).thenReturn(Optional.of(getMockDelivery()));
        List<Gate> gates = Collections.singletonList(getMockGate());
        Mockito.when(gateRepository.findByDeliveryId(MOCK_OK_ID)).thenReturn(gates);

        assertThat(postOfficeService.getDeliveryDTO(MOCK_OK_ID).getId(), is(getDeliveryDTO().getId()));
        assertThat(postOfficeService.getDeliveryDTO(MOCK_NOK_ID), is(nullValue()));
    }

    private static Delivery getMockDelivery() {
        return Delivery.builder().id(MOCK_OK_ID).build();
    }

    private static PostOffice getMockPostOffice() {
        return PostOffice.builder().id(MOCK_OK_ID).build();
    }

    private static Gate getMockGate() {
        return Gate.builder().id(MOCK_OK_ID).build();
    }

    private static DeliveryDTO getDeliveryDTO() {
        return DeliveryDTO.builder().id(MOCK_OK_ID).build();
    }

}