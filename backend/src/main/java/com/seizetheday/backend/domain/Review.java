package com.seizetheday.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table( name="reviews",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"activity_id", "author_user_id"})} )
@Setter
@Getter

public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_user_id", nullable = false)
    private User authorUser;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating;

    @NotBlank
    @Size(min = 1, max = 1000)
    private String comment;

    @NotNull
    @Column(nullable = false)
    private Instant createdAt;

    private String responseText;

    @ManyToOne
    @JoinColumn(name = "response_by_user_id")
    private User responseByUser;

    private Instant responseAt;

    @PrePersist
    void onCreate() {
        if (createdAt == null) {
            createdAt = Instant.now(); // store UTC
        }
    }
}
