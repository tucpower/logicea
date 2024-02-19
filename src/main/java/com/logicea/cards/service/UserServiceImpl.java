package com.logicea.cards.service;

import com.logicea.cards.model.dto.user.RegisterUserDTO;
import com.logicea.cards.model.entity.User;
import com.logicea.cards.model.exception.ResourceNotFoundException;
import com.logicea.cards.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User findByEmail(String email) throws ResourceNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public User createUser(RegisterUserDTO registerUserDTO) {
        User user = User.builder()
                .email(registerUserDTO.getEmail())
                .password(passwordEncoder.encode(registerUserDTO.getPassword()))
                .role(registerUserDTO.getRole())
                .build();

        return userRepository.save(user);
    }
}
