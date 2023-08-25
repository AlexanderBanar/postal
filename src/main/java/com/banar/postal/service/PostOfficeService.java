package com.banar.postal.service;

import com.banar.postal.dto.DeliveryDTO;
import com.banar.postal.model.Delivery;
import com.banar.postal.model.Gate;
import com.banar.postal.model.PostOffice;
import com.banar.postal.repository.DeliveryRepository;
import com.banar.postal.repository.GateRepository;
import com.banar.postal.repository.PostOfficeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class PostOfficeService {
    private DeliveryRepository deliveryRepository;
    private PostOfficeRepository postOfficeRepository;
    private GateRepository gateRepository;

    public Delivery register(Delivery delivery) {
        return deliveryRepository.saveAndFlush(delivery);
    }

    public boolean processArrival(Long deliveryId, Long postOfficeId) {
        Delivery delivery = deliveryRepository.findById(deliveryId).orElse(null);
        PostOffice postOffice = postOfficeRepository.findById(postOfficeId).orElse(null);

        if (delivery == null || postOffice == null) {
            return false;
        }

        Gate gate = Gate.builder()
                .deliveryId(delivery.getId())
                .postOfficeId(postOffice.getId())
                .arrivalDate(LocalDate.now())
                .build();

        return gateRepository.saveAndFlush(gate).getId() != null;
    }

    public boolean processDeparture(Long deliveryId, Long postOfficeId) {
        Gate gate = gateRepository.findByDeliveryIdAndPostOfficeId(deliveryId, postOfficeId);

        if (gate == null) {
            return false;
        }

        gate.setDepartureDate(LocalDate.now());
        gateRepository.saveAndFlush(gate);

        return true;
    }

    public boolean processReceipt(Long deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId).orElse(null);

        if (delivery == null) {
            return false;
        }

        Gate gateToClose = gateRepository.findByDeliveryIdAndDepartureDateIsNull(deliveryId);

        LocalDate closureDate = LocalDate.now();

        if (gateToClose != null) {
            gateToClose.setDepartureDate(closureDate);
            gateRepository.saveAndFlush(gateToClose);
        }

        delivery.setReceived(true);
        deliveryRepository.saveAndFlush(delivery);

        return true;
    }

    public DeliveryDTO getDeliveryDTO(Long deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId).orElse(null);

        if (delivery == null) {
            return null;
        }

        List<Gate> gates = gateRepository.findByDeliveryId(deliveryId);

        return DeliveryDTO.convertToDeliveryDTO(delivery, gates);
    }
}
