package com.logicea.cards.model.dto.user;

import lombok.Data;

@Data
public class RegisterUserDTO {
    private String email;
    private String password;
    private String role;
}
