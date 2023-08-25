package com.banar.postal.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "post_office")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostOffice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "index")
    private String index;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

}
