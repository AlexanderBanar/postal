package com.banar.postal.model;

import jakarta.persistence.*;
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
    private long id;

    @Column(name = "receiver_index")
    private String receiverIndex;

    @Column(name = "receiver_address")
    private String receiverAddress;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "delivery_type")
    @Enumerated(EnumType.STRING)
    private DeliveryType type = DeliveryType.UNDEFINED;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "delivery", orphanRemoval = true)
    @JoinColumn(name = "delivery_id")
    private List<Gate> gates;

}
