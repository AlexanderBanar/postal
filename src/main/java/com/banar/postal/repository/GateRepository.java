package com.banar.postal.repository;

import com.banar.postal.model.Gate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GateRepository extends JpaRepository<Gate, Long> {

    Gate findByDeliveryIdAndPostOfficeId(Long deliveryId, Long postOfficeId);

    Gate findByDeliveryIdAndDepartureDateIsNull(Long deliveryId);
}
