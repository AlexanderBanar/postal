package com.banar.postal.controller;

import com.banar.postal.dto.DeliveryDTO;
import com.banar.postal.model.Delivery;
import com.banar.postal.service.PostOfficeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

import static com.banar.postal.dto.DeliveryDTO.convertToDeliveryDTO;
import static com.banar.postal.dto.DeliveryDTO.convertToDelivery;

@RestController
@AllArgsConstructor
public class PostOfficeController {
    public static final String REGISTER_NEW_DELIVERY = "/register";
    public static final String ARRIVE_AT_POST_OFFICE = "/arrive/{deliveryId}/{postOfficeId}";
    public static final String DEPART_FROM_POST_OFFICE = "/depart/{deliveryId}/{postOfficeId}";
    public static final String RECEIVE_BY_ADDRESSEE = "/receive/{deliveryId}";
    public static final String GET_STATUS = "/status/{deliveryId}";

    private PostOfficeService service;

    @PostMapping(value = REGISTER_NEW_DELIVERY, consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<DeliveryDTO> registerDelivery(@Valid @RequestBody DeliveryDTO delivery) {
        Delivery registeredDelivery = service.register(convertToDelivery(delivery));

        if (registeredDelivery.getId() == null) {
            throw new IllegalArgumentException("something went wrong while new delivery registration");
        }

        return ResponseEntity.ok().body(convertToDeliveryDTO(registeredDelivery, Collections.emptyList()));
    }

    @PostMapping(value = ARRIVE_AT_POST_OFFICE, consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Boolean> arriveAtPostOffice(@NotNull @PathVariable("deliveryId") Long deliveryId, @NotNull @PathVariable("postOfficeId") Long postOfficeId) {
        if (service.processArrival(deliveryId, postOfficeId)) {
            return ResponseEntity.ok(Boolean.TRUE);
        }

        throw new IllegalArgumentException("Arrival at post office not processed! Wrong delivery id or post office id!");
    }

    @PostMapping(value = DEPART_FROM_POST_OFFICE, consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Boolean> departFromPostOffice(@NotNull @PathVariable("deliveryId") Long deliveryId, @NotNull @PathVariable("postOfficeId") Long postOfficeId) {
        if (service.processDeparture(deliveryId, postOfficeId)) {
            return ResponseEntity.ok(Boolean.TRUE);
        }

        throw new IllegalArgumentException("Departure from post office not processed! Wrong delivery id or post office id!");
    }

    @PostMapping(value = RECEIVE_BY_ADDRESSEE, consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Boolean> receiveByAddressee(@NotNull @PathVariable("deliveryId") Long deliveryId) {
        if (service.processReceipt(deliveryId)) {
            return ResponseEntity.ok(Boolean.TRUE);
        }

        throw new IllegalArgumentException("Receipt not processed! Wrong delivery id!");
    }

    @GetMapping(value = GET_STATUS, consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<DeliveryDTO> getStatus(@NotNull @PathVariable("deliveryId") Long deliveryId) {
        DeliveryDTO deliveryDTO = service.getDeliveryDTO(deliveryId);

        if (deliveryDTO == null) {
            throw new IllegalArgumentException("Wrong delivery id!");
        }

        return ResponseEntity.ok().body(deliveryDTO);
    }

}
