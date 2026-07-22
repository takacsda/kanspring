package com.takacsda.kanspring.card.application;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.takacsda.kanspring.card.domain.Card;
import com.takacsda.kanspring.card.domain.CardPriority;
import com.takacsda.kanspring.card.infrastructure.InMemoryCardRepository;
import com.takacsda.kanspring.card.web.dto.UpdateCardRequest;


public class CardServiceTest {
    private static CardService cardService;

    @BeforeAll
    public static void setup() {
        
    }

    @BeforeEach
    public void init() {
        InMemoryCardRepository inMemoryCardRepository = new InMemoryCardRepository();
        cardService = new CardService(inMemoryCardRepository);
        cardService.createCard(
            "First card created",
            "This is the first test card", 
            CardPriority.MEDIUM,
            UUID.randomUUID(),
            null);
        cardService.createCard(
            "Second card created",
            "This is the second test card", 
            CardPriority.LOW,
            UUID.randomUUID(),
            null);
    }

    @Nested
    class findByid {
        @Test
        public void returnExistingCard() {
            Card created = cardService.createCard(
                "Test findbyid",
                null,
                null,
                UUID.randomUUID(),
                null);
            UUID createdId = created.getId();
            Optional<Card> result = cardService.findById(createdId);

            assertTrue(result.isPresent());
            assertEquals(createdId, result.get().getId());
        }
        @Test
        public void returnNullIfNotFound() {
            Optional<Card> result = cardService.findById(UUID.randomUUID());
            assertTrue(result.isEmpty());
        }
    }

    @Nested
    class deleteById {
        @Test
        public void returnTrueOnDelete() {
            Card created = cardService.createCard(
                "Test findbyid",
                null,
                null,
                UUID.randomUUID(),
                null);
            UUID createdId = created.getId();
            assertTrue(cardService.deleteById(createdId));
        }
        @Test
        public void returnFalseOnDelete() {
            assertFalse(cardService.deleteById(UUID.randomUUID()));
        }
    }

    @Test
    public void findAllTest() {
        List<Card> allCards = cardService.findAll();
        assertEquals(2, allCards.size());
    }

    @Nested
    class updateCard {
        private UUID createdId;
        private UpdateCardRequest request;
        Card updated;

        @BeforeEach
        public void initUpdate() {
        Card created = cardService.createCard(
            "Test title",
            null,
            null,
            UUID.randomUUID(),
            null);
        createdId = created.getId();
        request = new UpdateCardRequest();
        }

        @Test
        public void updateCardTitle() {
            request.setTitle("New updated title");
            updated = cardService.updateCard(createdId, request).get();
        assertAll(
            () -> assertEquals("New updated title", updated.getTitle()),
            () -> assertEquals(null, updated.getDescription()),
            () -> assertEquals(CardPriority.MEDIUM, updated.getPriority()),
            () -> assertNull(updated.getAssigneeId())
        );
        }

        @Test
        public void updateCardDescription() {
            request.setDescription("New updated description");
            updated = cardService.updateCard(createdId, request).get();
            assertAll(
                () -> assertEquals("Test title", updated.getTitle()),
                () -> assertEquals("New updated description", updated.getDescription()),
                () -> assertEquals(CardPriority.MEDIUM, updated.getPriority()),
                () -> assertEquals(null, updated.getAssigneeId())
            );

            request.setDescription(null);
            updated = cardService.updateCard(createdId, request).get();
            assertEquals(null, updated.getDescription());
        }

        @Test
        public void updateCardPriority() {
            request.setPriority(CardPriority.HIGH);
            updated = cardService.updateCard(createdId, request).get();
            assertAll(
                () -> assertEquals("Test title", updated.getTitle()),
                () -> assertEquals(null, updated.getDescription()),
                () -> assertEquals(CardPriority.HIGH, updated.getPriority()),
                () -> assertEquals(null, updated.getAssigneeId())
            );
        }

        @Test
        public void updateCardAssigneeId() {
            UUID assigneeId = UUID.randomUUID();
            request.setAssigneeId(assigneeId);
            updated = cardService.updateCard(createdId, request).get();
            assertAll(
                () -> assertEquals("Test title", updated.getTitle()),
                () -> assertEquals(null, updated.getDescription()),
                () -> assertEquals(CardPriority.MEDIUM, updated.getPriority()),
                () -> assertEquals(assigneeId, updated.getAssigneeId())
            );

            request.setAssigneeId(null);
            updated = cardService.updateCard(createdId, request).get();
            assertEquals(null, updated.getAssigneeId());
        }

    }
}
