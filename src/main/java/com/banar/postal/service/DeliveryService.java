package com.banar.postal.service;

import com.banar.postal.model.Delivery;
import com.banar.postal.repository.DeliveryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeliveryService {
    private DeliveryRepository repository;

    public Delivery register(Delivery delivery) {
        return repository.saveAndFlush(delivery);
    }
}
