package com.takacsda.kanspring.card.domain;

import java.time.Instant;
import java.util.UUID;

public class Card {

    private final UUID id;
    private UUID assigneeId;
    private final UUID ownerId;
    private String title;
    private String description;
    private CardPriority priority;
    private Instant updatedAt;
    private final Instant createdAt;

    public Card(String title, String description, CardPriority priority, UUID ownerId) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title should not be null.");
        }
        this.title = title;

        if (priority == null) {
            this.priority = CardPriority.MEDIUM;
        } else this.priority = priority;

        this.id = UUID.randomUUID();

        if (ownerId == null) {
            throw new IllegalArgumentException("OwnerID should not be null.");
        }
        this.ownerId = ownerId;

        this.description = description;
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    public void changePriority(CardPriority priority) {
        if (priority == null) {
            throw new IllegalArgumentException("OwnerID should not be null.");
        }
        this.priority = priority;
        updateTime();
    }

    public void changeTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title should not be null.");
        }
        this.title = title;
        updateTime();
    }

    public void changeDescription(String description) {
        this.description = description;
        updateTime();
    }

    public void assignToUser(UUID assigneeId) {
        this.assigneeId = assigneeId;
        updateTime();
    }

    private void updateTime() {
        this.updatedAt = Instant.now();
    }

    public UUID getId() {
        return this.id;
    }

    public UUID getOwnerId() {
        return this.ownerId;
    }

    public UUID getAssigneeId() {
        return this.assigneeId;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public CardPriority getPriority() {
        return this.priority;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }
}
