package com.takacsda.kanspring.card.application;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.takacsda.kanspring.card.domain.Card;

public interface CardRepository {
    void save(Card card);
    void deleteById(UUID id);
    Optional<Card> findById(UUID id);
    List<Card> findAll();
}
