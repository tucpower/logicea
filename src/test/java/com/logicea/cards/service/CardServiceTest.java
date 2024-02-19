package com.logicea.cards.service;

import com.logicea.cards.model.dto.card.CardDTO;
import com.logicea.cards.model.entity.Card;
import com.logicea.cards.model.entity.User;
import com.logicea.cards.repository.CardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardServiceImpl cardService;

    @Test
    void findAllUsingFilters() {

        User user = User.builder()
                .email("email@email.com")
                .build();
        List<Card> cards = new ArrayList<>();
        Card cardA = Card.builder()
                .name("nameA")
                .user(user)
                .build();
        cards.add(cardA);
        Card cardB = Card.builder()
                .name("nameB")
                .user(user)
                .build();
        cards.add(cardB);
        Page<Card> cardsPage = new PageImpl<>(cards);

        when(cardRepository.findAllByNameContainingAndColorContainingAndStatusContainingAndDateOfCreation(
                anyString(), anyString(), anyString(), any(), any()
        )).thenReturn(cardsPage);

        List<CardDTO> cardDTOs = new ArrayList<>();
        CardDTO cardDTO1 = CardDTO.builder()
                .name("nameA")
                .user("email@email.com")
                .build();
        cardDTOs.add(cardDTO1);
        CardDTO cardDTO2 = CardDTO.builder()
                .name("nameB")
                .user("email@email.com")
                .build();
        cardDTOs.add(cardDTO2);

        List<CardDTO> returnedCardDTOs = cardService.findAllUsingFilters(
                "name1",
                "#123456",
                "To Do",
                LocalDate.now(),
                null
        );

        assertEquals(cardDTOs, returnedCardDTOs);
    }

    @Test
    void findAllUsingFiltersByUser() {
    }

    @Test
    void findByName() {
    }

    @Test
    void findById() {
    }

    @Test
    void createCard() {
    }

    @Test
    void updateById() {
    }

    @Test
    void deleteById() {
    }
}