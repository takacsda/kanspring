package com.takacsda.kanspring.card.infrastructure;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.takacsda.kanspring.card.application.CardRepository;
import com.takacsda.kanspring.card.domain.Card;
import com.takacsda.kanspring.card.domain.CardPriority;

import jakarta.annotation.PostConstruct;

@Repository
public class InMemoryCardRepository implements CardRepository {

    private Map<UUID, Card> cardsList = new ConcurrentHashMap<>();

    @Override
    public void deleteById(UUID cardId) {
        cardsList.remove(cardId);
    }

    @Override
    public void save(Card card) {
        cardsList.put(card.getId(), card);
    }

    @Override
    public Optional<Card> findById(UUID cardId) {
        return Optional.ofNullable(cardsList.get(cardId));
    }

    @Override
    public List<Card> findAll() {
        return List.copyOf(cardsList.values());
    }

    @PostConstruct
    public void post() {
        Card card = new Card("First title", "This is my first card", CardPriority.LOW, UUID.randomUUID(), null);
        cardsList.put(card.getId(), card);
    }
    
}
