package com.logicea.cards.model.dto.card;

import lombok.Data;

@Data
public class CreateCardDTO {
    private String name = "";
    private String description = "";
    private String color = "";
    private String user = "";
}
