package com.logicea.cards.service;

import com.logicea.cards.model.dto.user.RegisterUserDTO;
import com.logicea.cards.model.entity.User;
import com.logicea.cards.model.exception.ResourceNotFoundException;

import java.util.List;

public interface UserService {

    public List<User> findAll();
    User findByEmail(String email) throws ResourceNotFoundException;
    boolean existsByEmail(String email);
    User createUser(RegisterUserDTO registerUserDTO);
}
