package com.logicea.cards.service;

import com.logicea.cards.model.dto.card.CardDTO;
import com.logicea.cards.model.entity.Card;
import com.logicea.cards.model.entity.User;
import com.logicea.cards.repository.CardRepository;
import com.logicea.cards.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void findAll() {
        List<User> users = new ArrayList<>();
        User userA = User.builder()
                .email("email1@email.com")
                .build();
        users.add(userA);
        User userB = User.builder()
                .email("email2@email.com")
                .build();
        users.add(userB);

        when(userRepository.findAll()).thenReturn(users);

        List<User> returnedUsers = userService.findAll();

        assertEquals(users, returnedUsers);
    }

    @Test
    void findByEmail() {
    }

    @Test
    void existsByEmail() {
    }

    @Test
    void createUser() {
    }
}