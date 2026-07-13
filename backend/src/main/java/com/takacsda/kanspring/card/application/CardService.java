package com.takacsda.kanspring.card.application;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.takacsda.kanspring.card.domain.Card;
import com.takacsda.kanspring.card.domain.CardPriority;

@Service
public class CardService {
    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }
    
    public List<Card> findAll(){
        return cardRepository.findAll();
    }
    
    public Optional<Card> findById(UUID cardId) {
        return cardRepository.findById(cardId);
    }

    public Card createCard(
        String title,
          String description,
          CardPriority priority,
          UUID ownerId,
          UUID assigneeId
    ) {
        Card card = new Card(title, description, priority, ownerId, assigneeId);
        cardRepository.save(card);
        return card;
    }

    public void save(Card card) {
        cardRepository.save(card);
    }

    public boolean deleteById(UUID cardId) {
        if (findById(cardId).isEmpty()) return false;

        cardRepository.deleteById(cardId);
        return true;
    }

    public Optional<Card> assignToUser(UUID cardId, UUID assigneeId) {
        return cardRepository.findById(cardId).
                map(card -> {
                    card.assignToUser(assigneeId);
                    cardRepository.save(card);
                    return card;
                });
    }

    public Optional<Card> changeTitle(UUID cardId, String title) {
        return cardRepository.findById(cardId).
                map(card -> {
                    card.changeTitle(title);
                    cardRepository.save(card);
                    return card;
                });
    }

    public Optional<Card> changeDescription(UUID cardId, String description) {
        return cardRepository.findById(cardId).
                map(card -> {
                    card.changeDescription(description);
                    cardRepository.save(card);
                    return card;
                });
    }

    public Optional<Card> changePriority(UUID cardId, CardPriority cardPriority) {
        return cardRepository.findById(cardId).
                map(card -> {
                    card.changePriority(cardPriority);
                    cardRepository.save(card);
                    return card;
                });
    }

    public Card getCardById(UUID cardId) {
        return cardRepository.findById(cardId).orElseThrow();
    }
}
