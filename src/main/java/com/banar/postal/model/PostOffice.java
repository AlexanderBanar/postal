package com.banar.postal.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "post_office")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PostOffice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "index")
    private String index;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "postOffice", orphanRemoval = true)
    @JoinColumn(name = "post_office_id")
    private List<Gate> gates;

}
