package com.logicea.cards.service;

import com.logicea.cards.model.ApplicationConstants;
import com.logicea.cards.model.dto.card.CardDTO;
import com.logicea.cards.model.dto.card.CreateCardDTO;
import com.logicea.cards.model.dto.card.UpdateCardDTO;
import com.logicea.cards.model.entity.Card;
import com.logicea.cards.model.entity.User;
import com.logicea.cards.model.exception.ResourceNotFoundException;
import com.logicea.cards.repository.CardRepository;
import com.logicea.cards.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<CardDTO> findAllUsingFilters(String name, String color, String status, LocalDate dateOfCreation, Pageable pageable) {
        List<CardDTO> cardDTOs = new ArrayList<>();

        List<Card> cards;
        if(dateOfCreation != null) {
            cards = cardRepository.findAllByNameContainingAndColorContainingAndStatusContainingAndDateOfCreation(
                    name,
                    color,
                    status,
                    dateOfCreation,
                    pageable).toList();
        } else {
            cards = cardRepository.findAllByNameContainingAndColorContainingAndStatusContaining(
                    name,
                    color,
                    status,
                    pageable).toList();
        }

        for (Card card : cards) {
            CardDTO cardDTO = CardDTO.builder()
                    .name(card.getName())
                    .description(card.getDescription())
                    .color(card.getColor())
                    .status(card.getStatus())
                    .dateOfCreation(card.getDateOfCreation())
                    .user(card.getUser().getEmail())
                    .build();
            cardDTOs.add(cardDTO);
        }

        return cardDTOs;
    }

    @Override
    public List<CardDTO> findAllUsingFiltersByUser(String name, String color, String status, LocalDate dateOfCreation, User user, Pageable pageable) {
        List<CardDTO> cardDTOs = new ArrayList<>();

        List<Card> cards;
        if(dateOfCreation != null) {
            cards = cardRepository.findAllByUserAndNameContainingAndColorContainingAndStatusContainingAndDateOfCreation(
                    user,
                    name,
                    color,
                    status,
                    dateOfCreation,
                    pageable).toList();
        } else {
            cards = cardRepository.findAllByUserAndNameContainingAndColorContainingAndStatusContaining(
                    user,
                    name,
                    color,
                    status,
                    pageable).toList();
        }

        for (Card card : cards) {
            CardDTO cardDTO = CardDTO.builder()
                    .name(card.getName())
                    .description(card.getDescription())
                    .color(card.getColor())
                    .status(card.getStatus())
                    .dateOfCreation(card.getDateOfCreation())
                    .user(card.getUser().getEmail())
                    .build();
            cardDTOs.add(cardDTO);
        }

        return cardDTOs;
    }

    @Override
    public CardDTO findByName(String name) throws ResourceNotFoundException {

        Card card = cardRepository.findByName(name).orElseThrow(ResourceNotFoundException::new);

        return CardDTO.builder()
                .name(card.getName())
                .description(card.getDescription())
                .color(card.getColor())
                .status(card.getStatus())
                .dateOfCreation(card.getDateOfCreation())
                .user(card.getUser().getEmail())
                .build();
    }

    @Override
    public Card findById(int id) throws ResourceNotFoundException {
        return cardRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public CardDTO createCard(CreateCardDTO createCardDTO) {
        Card card = Card.builder()
                .name(createCardDTO.getName())
                .description(createCardDTO.getDescription())
                .color(createCardDTO.getColor())
                .status(ApplicationConstants.CARD_STATUS_TO_DO)
                .dateOfCreation(LocalDate.now())
                .user(userRepository.findByEmail(createCardDTO.getUser()).orElseThrow(ResourceNotFoundException::new))
                .build();

        card = cardRepository.save(card);

        return CardDTO.builder()
                .name(card.getName())
                .description(card.getDescription())
                .color(card.getColor())
                .status(card.getStatus())
                .dateOfCreation(card.getDateOfCreation())
                .user(card.getUser().getEmail())
                .build();
    }

    @Override
    public CardDTO updateById(Card existingCard, UpdateCardDTO updateCardDTO) throws ResourceNotFoundException {
        if(updateCardDTO.getName() != null) {
            existingCard.setName(updateCardDTO.getName());
        }
        existingCard.setDescription(updateCardDTO.getDescription());
        existingCard.setColor(updateCardDTO.getColor());
        if(updateCardDTO.getStatus() != null) {
            existingCard.setStatus(updateCardDTO.getStatus());
        }
        Card savedCard = cardRepository.save(existingCard);

        return CardDTO.builder()
                .name(savedCard.getName())
                .description(savedCard.getDescription())
                .color(savedCard.getColor())
                .status(savedCard.getStatus())
                .dateOfCreation(savedCard.getDateOfCreation())
                .user(savedCard.getUser().getEmail())
                .build();
    }

    @Override
    public void deleteById(int id) throws ResourceNotFoundException {
        if(cardRepository.existsById(id)) {
            cardRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException();
        }
    }
}
