package com.seizetheday.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name="activities")
@Setter
@Getter
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 120)
    private String title;

    @NotBlank
    @Size(min = 10, max = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false)
    private Status status;

    @OneToOne(mappedBy = "activity", cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    private Address address;

    @ManyToOne(optional = false)
    private User ownerUser;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Review> reviews;

    @NotNull
    @Column(nullable = false)
    private Instant createdAt;

    @PrePersist
    void onCreate() {
        if (createdAt == null) {
            createdAt = Instant.now(); // store UTC
        }
    }

}
