package com.logicea.cards.repository;

import com.logicea.cards.model.entity.Card;
import com.logicea.cards.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Integer> {

    Optional<Card> findByName(String name);

    // FIND CARDS FOR ADMINS
    Page<Card> findAllByNameContainingAndColorContainingAndStatusContainingAndDateOfCreation(String name, String color, String status, LocalDate dateOfCreation, Pageable pageable);
    Page<Card> findAllByNameContainingAndColorContainingAndStatusContaining(String name, String color, String status, Pageable pageable);


    // FIND CARDS FOR MEMBERS
    Page<Card> findAllByUserAndNameContainingAndColorContainingAndStatusContainingAndDateOfCreation(User user, String name, String color, String status, LocalDate dateOfCreation, Pageable pageable);
    Page<Card> findAllByUserAndNameContainingAndColorContainingAndStatusContaining(User user, String name, String color, String status, Pageable pageable);
}
