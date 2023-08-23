package com.banar.postal.dto;

import com.banar.postal.model.Delivery;
import com.banar.postal.model.DeliveryType;
import com.banar.postal.model.Gate;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class DeliveryDTO {
    private Long id;
    private String receiverIndex;
    private String receiverAddress;
    private String receiverName;
    private boolean isReceived = false;
    private DeliveryType type = DeliveryType.UNDEFINED;
    private List<Gate> gates;

    public static Delivery convertToDelivery(DeliveryDTO deliveryDTO) {
        return new Delivery(
                deliveryDTO.getId(),
                deliveryDTO.getReceiverIndex(),
                deliveryDTO.getReceiverAddress(),
                deliveryDTO.getReceiverName(),
                deliveryDTO.isReceived(),
                deliveryDTO.getType()
        );
    }

    public static DeliveryDTO convertToDeliveryDTO(Delivery delivery, List<Gate> gates) {
        return new DeliveryDTO(
                delivery.getId(),
                delivery.getReceiverIndex(),
                delivery.getReceiverAddress(),
                delivery.getReceiverName(),
                delivery.isReceived(),
                delivery.getType(),
                gates
        );
    }
}
