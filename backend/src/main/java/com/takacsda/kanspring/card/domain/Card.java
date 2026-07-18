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

    public Card(String title, String description, CardPriority priority, UUID ownerId, UUID assigneeId) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title must not be blank.");
        }
        if (ownerId == null) {
            throw new IllegalArgumentException("Owner ID must not be null.");
        }

        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.priority = priority == null ? CardPriority.MEDIUM : priority;
        this.ownerId = ownerId;
        this.assigneeId = assigneeId;
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    public void changePriority(CardPriority priority) {
        if (priority == null) {
            throw new IllegalArgumentException("Priority should not be null.");
        }
        this.priority = priority;
        updateTime();
    }

    public void changeTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title should not be null or blank.");
        }
        this.title = title;
        updateTime();
    }

    public void changeDescription(String description) {
        this.description = description;
        updateTime();
    }

    public void assignToUser(UUID assigneeId) {
        if (assigneeId == null) {
            this.assigneeId = null;
        }
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
