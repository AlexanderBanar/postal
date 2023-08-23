package com.banar.postal.repository;

import com.banar.postal.model.Gate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GateRepository extends JpaRepository<Gate, Long> {

    Gate findByDeliveryIdAndPostOfficeId(Long deliveryId, Long postOfficeId);

    Gate findByDeliveryIdAndDepartureDateIsNull(Long deliveryId);

    List<Gate> findByDeliveryId(Long deliveryId);
}
