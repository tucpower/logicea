package com.logicea.cards.controller;

import com.logicea.cards.model.ApplicationConstants;
import com.logicea.cards.model.dto.user.LoginRequestDTO;
import com.logicea.cards.model.dto.user.LoginResponseDTO;
import com.logicea.cards.model.dto.user.RegisterUserDTO;
import com.logicea.cards.model.dto.user.UserDTO;
import com.logicea.cards.model.entity.User;
import com.logicea.cards.security.JwtUtilities;
import com.logicea.cards.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtilities jwtUtilities;

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserDTO registerUserDTO) {
        if(userService.existsByEmail(registerUserDTO.getEmail())) {
            return new ResponseEntity<>("User email is taken!", HttpStatus.BAD_REQUEST);
        }

        if(!registerUserDTO.getRole().equals(ApplicationConstants.ROLE_ADMIN) &&
                !registerUserDTO.getRole().equals(ApplicationConstants.ROLE_MEMBER)) {
            return new ResponseEntity<>("Invalid role for user!", HttpStatus.BAD_REQUEST);
        }

        User user = userService.createUser(registerUserDTO);
        UserDTO userDTO = UserDTO.builder()
                .email(user.getEmail())
                .role(user.getRole())
                .build();

        return ResponseEntity.ok(userDTO);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {

        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    loginRequestDTO.getEmail(),
                                    loginRequestDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtUtilities.generateToken(authentication);

            return ResponseEntity.ok(new LoginResponseDTO(authentication.getName(), token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username or password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }
}