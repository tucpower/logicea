package com.logicea.cards.model.dto.card;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CardDTO {
    private String name;
    private String description;
    private String color;
    private String status;
    private LocalDate dateOfCreation;
    private String user;
}
