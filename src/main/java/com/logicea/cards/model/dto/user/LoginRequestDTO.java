package com.logicea.cards.model.dto.user;

import lombok.Data;

@Data
public class LoginRequestDTO {

    private String email;
    private String password;
}
