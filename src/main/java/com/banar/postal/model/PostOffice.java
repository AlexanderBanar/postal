package com.banar.postal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Index is mandatory")
    @Size(min = 2, max = 15, message = "Index must be from 2 to 15 letters length")
    @Column(name = "post_office_index")
    private String index;

    @Column(name = "post_office_name")
    private String name;

    @Column(name = "address")
    private String address;

}
