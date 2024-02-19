package com.logicea.cards.model.dto.user;

import com.logicea.cards.model.entity.Card;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
public class UserDTO {

    private String email;
    private String role;
}
