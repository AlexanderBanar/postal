package com.banar.postal.service;

import com.banar.postal.model.Delivery;
import com.banar.postal.model.Gate;
import com.banar.postal.model.PostOffice;
import com.banar.postal.repository.DeliveryRepository;
import com.banar.postal.repository.GateRepository;
import com.banar.postal.repository.PostOfficeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
                .delivery(delivery)
                .postOffice(postOffice)
                .arrivalDate(LocalDate.now())
                .build();

        return gateRepository.saveAndFlush(gate).getId() != null;
    }
}
