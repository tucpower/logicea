package com.logicea.cards.controller;

import com.logicea.cards.model.ApplicationConstants;
import com.logicea.cards.model.dto.card.CardDTO;
import com.logicea.cards.model.dto.card.CreateCardDTO;
import com.logicea.cards.model.dto.card.UpdateCardDTO;
import com.logicea.cards.model.entity.Card;
import com.logicea.cards.model.entity.User;
import com.logicea.cards.model.exception.ResourceNotFoundException;
import com.logicea.cards.service.CardService;
import com.logicea.cards.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/v1/cards")
public class CardController {

    @Autowired
    private CardService cardService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(name = "name", defaultValue = "") String name,
            @RequestParam(name = "color", defaultValue = "") String color,
            @RequestParam(name = "status", defaultValue = "") String status,
            @RequestParam(name = "dateOfCreation", required = false) LocalDate dateOfCreation,
            Pageable pageable) {
        List<CardDTO> cards = new ArrayList<>();

        SimpleGrantedAuthority adminRole = new SimpleGrantedAuthority(ApplicationConstants.ROLE_ADMIN);
        SimpleGrantedAuthority memberRole = new SimpleGrantedAuthority(ApplicationConstants.ROLE_MEMBER);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // IF ADMIN => RETURN ALL CARDS
        // IF MEMBER => RETURN CARDS OWNED BY THE MEMBER
        if(authentication.getAuthorities().contains(adminRole)) {
            cards = cardService.findAllUsingFilters(name, color, status, dateOfCreation, pageable);

        } else if(authentication.getAuthorities().contains(memberRole)) {
            User user = userService.findByEmail(authentication.getName());
            cards = cardService.findAllUsingFiltersByUser(name, color, status, dateOfCreation, user, pageable);
        }

        return ResponseEntity.ok(cards);
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name) {

        CardDTO cardDTO = cardService.findByName(name);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // IF MEMBER OWNS THE CARD OR WE HAVE AN ADMIN => RETURN THE CARD
        if(authentication.getName().equals(cardDTO.getUser())
                || authentication.getAuthorities().contains(new SimpleGrantedAuthority(ApplicationConstants.ROLE_ADMIN))) {
            return ResponseEntity.ok(cardDTO);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not have access to the card");
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateCardDTO createCardDTO) {

        if(createCardDTO.getName() == null || createCardDTO.getName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Card name is mandatory");
        }

        if(createCardDTO.getColor() != null && !createCardDTO.getColor().isEmpty()) {
            String COLOR_REGEX = "^#([a-fA-F0-9]{6})$";
            if (!Pattern.matches(COLOR_REGEX, createCardDTO.getColor())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Color is invalid");
            }
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        createCardDTO.setUser(authentication.getName());

        return ResponseEntity.ok(cardService.createCard(createCardDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable int id, @RequestBody UpdateCardDTO updateCardDTO) {

        Card card = cardService.findById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // IF MEMBER OWNS THE CARD OR WE HAVE AN ADMIN
        if(authentication.getName().equals(card.getUser().getEmail())
                || authentication.getAuthorities().contains(new SimpleGrantedAuthority(ApplicationConstants.ROLE_ADMIN))) {

            // COLOR VALIDATION
            if(updateCardDTO.getColor() != null && !updateCardDTO.getColor().isEmpty()) {
                String COLOR_REGEX = "^#([a-fA-F0-9]{6})$";
                if (!Pattern.matches(COLOR_REGEX, updateCardDTO.getColor())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Color is invalid");
                }
            }

            // STATUS VALIDATION
            String updatedStatus = updateCardDTO.getStatus();
            if(updatedStatus != null && !updatedStatus.isEmpty()) {
                if (!updatedStatus.equals(ApplicationConstants.CARD_STATUS_TO_DO) &&
                        !updatedStatus.equals(ApplicationConstants.CARD_STATUS_IN_PROGRESS) &&
                        !updatedStatus.equals(ApplicationConstants.CARD_STATUS_DONE)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Status is invalid");
                }
            }

            return ResponseEntity.ok(cardService.updateById(card, updateCardDTO));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Member does not have access to the card");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable int id) {
        try {
            Card card = cardService.findById(id);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            // IF MEMBER OWNS THE CARD OR WE HAVE AN ADMIN
            if(authentication.getName().equals(card.getUser().getEmail())
                    || authentication.getAuthorities().contains(new SimpleGrantedAuthority(ApplicationConstants.ROLE_ADMIN))) {
                cardService.deleteById(id);
                return ResponseEntity.ok("Card was deleted successfully");
            }
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Card does not exist");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Member does not have access to the card");
    }
}
