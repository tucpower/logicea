package com.logicea.cards.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class LoginResponseDTO {

    private String username;
    private String accessToken;
    private String tokenType = "Bearer";

    public LoginResponseDTO(String username, String accessToken) {
        this.username = username;
        this.accessToken = accessToken;
    }
}
