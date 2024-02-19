package com.logicea.cards.service;

import com.logicea.cards.model.dto.card.CardDTO;
import com.logicea.cards.model.dto.card.CreateCardDTO;
import com.logicea.cards.model.dto.card.UpdateCardDTO;
import com.logicea.cards.model.entity.Card;
import com.logicea.cards.model.entity.User;
import com.logicea.cards.model.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface CardService {
    public List<CardDTO> findAllUsingFilters(String name, String color, String status, LocalDate dateOfCreation, Pageable pageable);
    public List<CardDTO> findAllUsingFiltersByUser(String name, String color, String status, LocalDate dateOfCreation, User user, Pageable pageable);
    CardDTO findByName(String name) throws ResourceNotFoundException;
    Card findById(int id) throws ResourceNotFoundException;
    CardDTO createCard(CreateCardDTO createCardDTO);
    CardDTO updateById(Card existingCard, UpdateCardDTO updateCardDTO) throws ResourceNotFoundException;
    void deleteById(int id) throws ResourceNotFoundException;
}
