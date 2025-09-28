package com.seizetheday.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="addresses")
@Setter
@Getter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country;

    private String city;

    private String street;

    private Long number;

    private Long postalCode;

    private Long longitude;

    private Long latitude;

    @OneToOne
    @JoinColumn(name = "activity_id", nullable = false, unique = true)
    private Activity activity;

}
