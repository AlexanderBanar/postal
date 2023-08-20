package com.banar.postal.controller;

import com.banar.postal.model.Delivery;
import com.banar.postal.service.DeliveryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class PostOfficeController {
    private static final String REGISTER_NEW_DELIVERY = "/register";
    private static final String ARRIVE_AT_POST_OFFICE = "/arrive/{deliveryId}/{postOfficeId}";
    private static final String DEPART_FROM_POST_OFFICE = "/depart/{deliveryId}/{postOfficeId}";
    private static final String RECEIVE_BY_ADDRESSEE = "/receive/{deliveryId}";
    private static final String GET_STATUS = "/status/{deliveryId}";

    private DeliveryService deliveryService;

    @PostMapping(value = REGISTER_NEW_DELIVERY, consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Delivery> registerDelivery(@Valid @RequestBody Delivery delivery) {
        Delivery registeredDelivery = deliveryService.register(delivery);

        if (registeredDelivery.getId() == null) {
            throw new IllegalArgumentException("something went wrong while new delivery registration");
        }

        return ResponseEntity.ok().body(registeredDelivery);
    }

    @PostMapping(value = ARRIVE_AT_POST_OFFICE, consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Boolean> arriveAtPostOffice(@PathVariable("deliveryId") Long deliveryId, @PathVariable("postOfficeId") Long postOfficeId) {
        return null;
    }

    @PostMapping(value = DEPART_FROM_POST_OFFICE, consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Boolean> departFromPostOffice(@PathVariable("deliveryId") Long deliveryId, @PathVariable("postOfficeId") Long postOfficeId) {
        return null;
    }

    @PostMapping(value = RECEIVE_BY_ADDRESSEE, consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Void> receiveByAddressee(@PathVariable("deliveryId") Long deliveryId) {
        return null;
    }

    @GetMapping(value = GET_STATUS, consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Delivery> getStatus(@PathVariable("deliveryId") Long deliveryId) {
        return null;
    }

}
