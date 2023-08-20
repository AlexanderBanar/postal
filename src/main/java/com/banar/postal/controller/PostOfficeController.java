package com.banar.postal.controller;

import com.banar.postal.model.Delivery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostOfficeController {
    private static final String REGISTER_NEW_DELIVERY = "/register";
    private static final String ARRIVE_AT_POST_OFFICE = "/arrive/{deliveryId}/{postOfficeId}";
    private static final String DEPART_FROM_POST_OFFICE = "/depart/{deliveryId}/{postOfficeId}";
    private static final String RECEIVE_BY_ADDRESSEE = "/receive/{deliveryId}";
    private static final String GET_STATUS = "/status/{deliveryId}";

    @PostMapping(value = REGISTER_NEW_DELIVERY, consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Delivery> registerDelivery(@RequestBody Delivery delivery) {
        return null;
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
