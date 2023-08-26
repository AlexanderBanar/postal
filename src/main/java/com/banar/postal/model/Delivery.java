package com.banar.postal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "delivery")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "receiver_index")
    @NotBlank(message = "Receiver index is mandatory")
    @Size(min = 2, max = 15, message = "index must be from 2 to 15 letters length")
    private String receiverIndex;

    // TODO -> move validation to DeliveryDTO and check validation

    @Column(name = "receiver_address")
    @NotBlank(message = "Receiver address is mandatory")
    private String receiverAddress;

    @Column(name = "receiver_name")
    @NotBlank(message = "Receiver name is mandatory")
    private String receiverName;

    @Column(name = "is_received")
    private boolean isReceived = false;

    @Column(name = "delivery_type")
    @Enumerated(EnumType.STRING)
    private DeliveryType type = DeliveryType.UNDEFINED;

}
