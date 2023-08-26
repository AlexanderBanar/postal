package com.banar.postal.model;

import jakarta.persistence.*;
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
    private String receiverIndex;

    @Column(name = "receiver_address")
    private String receiverAddress;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "is_received")
    private boolean isReceived = false;

    @Column(name = "delivery_type")
    @Enumerated(EnumType.STRING)
    private DeliveryType type = DeliveryType.UNDEFINED;

}
