package com.takacsda.kanspring.card.application;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.takacsda.kanspring.card.domain.Card;
import com.takacsda.kanspring.card.domain.CardPriority;
import com.takacsda.kanspring.card.web.dto.UpdateCardRequest;

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

    public boolean deleteById(UUID cardId) {
        if (findById(cardId).isEmpty()) return false;

        cardRepository.deleteById(cardId);
        return true;
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

    public Optional<Card> updateCard (UUID cardId, UpdateCardRequest request) {
        return cardRepository.findById(cardId).
            map(card -> {
                if (request.hasTitle()) {
                    card.changeTitle(request.getTitle());
                    cardRepository.save(card);
                }
                if (request.hasDescription()) {
                    card.changeDescription(request.getDescription());
                    cardRepository.save(card);
                }
                if (request.hasPriority()) {
                    card.changePriority(request.getPriority());
                    cardRepository.save(card);
                }
                if (request.hasAssigneeId()) {
                    card.assignToUser(request.getAssigneeId());
                    cardRepository.save(card);
                    System.out.println(request);
                }
                return card;
            });
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
