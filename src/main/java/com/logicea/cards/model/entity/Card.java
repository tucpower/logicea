package com.logicea.cards.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String name;

    @NotNull
    private String description;

    @NotNull
    private String color;

    @NotBlank
    private String status;

    @NotNull
    private LocalDate dateOfCreation;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
}
