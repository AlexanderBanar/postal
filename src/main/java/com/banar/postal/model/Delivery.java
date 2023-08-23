package com.banar.postal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "delivery")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "receiver_index")
    @NotBlank(message = "Receiver index is mandatory")
    private String receiverIndex;

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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "delivery_id")
    private List<Gate> gates;

}
