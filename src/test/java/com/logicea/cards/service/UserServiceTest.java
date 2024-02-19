package com.logicea.cards.service;

import com.logicea.cards.model.entity.User;
import com.logicea.cards.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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