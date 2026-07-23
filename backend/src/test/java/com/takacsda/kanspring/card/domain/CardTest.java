package com.takacsda.kanspring.card.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CardTest {

    @Test
    public void throwExceptionOnConstructor() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            new Card(null, null, CardPriority.MEDIUM, UUID.randomUUID(), null);
        });
        assertEquals(IllegalArgumentException.class, exception.getClass());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            new Card("Test title", null, CardPriority.MEDIUM, null, null);
        });
        assertEquals(IllegalArgumentException.class, exception.getClass());
    }

    @Nested
    class changePriority{
        @Test
        public void updatePriority() {
            Card card = new Card("Test", null, CardPriority.MEDIUM, UUID.randomUUID(), null);

            card.changePriority(CardPriority.HIGH);
            assertEquals(CardPriority.HIGH, card.getPriority());
        }
        @Test
        public void throwExceptionOnUpdatePriority() {
            Card card = new Card("Test", null, CardPriority.MEDIUM, UUID.randomUUID(), null);
            Throwable exception = assertThrows(IllegalArgumentException.class, ()-> {
                card.changePriority(null);
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }
    }

    @Nested
    class changeTitle{
        @Test
        public void updateTitle() {
            Card card = new Card("Test", null, null, UUID.randomUUID(), null);

            card.changeTitle("New Title");
            assertEquals("New Title", card.getTitle());
        }
        @Test
        public void throwExceptionOnChangeTitle() {
            Card card = new Card("Test", null, null, UUID.randomUUID(), null);

            Throwable exception = assertThrows(IllegalArgumentException.class, ()-> {
                card.changeTitle(null);
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());

            exception = assertThrows(IllegalArgumentException.class, ()-> {
                card.changeTitle("");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }
    }

    @Test
    public void updateDescription(){
    Card card = new Card("Test", null, null, UUID.randomUUID(), null);

    card.changeDescription("Brand new");
    assertEquals("Brand new", card.getDescription());

    card.changeDescription(null);
    assertEquals(null, card.getDescription());
    }

    @Test
    public void assignToUserTest() {
        Card card = new Card("Test", null, null, UUID.randomUUID(), null);

        UUID assignedUserId = UUID.randomUUID();
        card.assignToUser(assignedUserId);
        assertEquals(assignedUserId, card.getAssigneeId());

        card.assignToUser(null);
        assertEquals(null, card.getAssigneeId());
    }
}
