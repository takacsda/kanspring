package com.takacsda.kanspring.card.web.dto;

import java.time.Instant;
import java.util.UUID;

import com.takacsda.kanspring.card.domain.Card;
import com.takacsda.kanspring.card.domain.CardPriority;

public record CardResponse(
    UUID id,
    UUID assigneeId,
    UUID ownerId,
    String title,
    String description,
    CardPriority priority,
    Instant updatedAt,
    Instant createdAt
) {
    public static CardResponse from (Card card) {
        return new CardResponse(
            card.getId(),
            card.getAssigneeId(),
            card.getOwnerId(),
            card.getTitle(),
            card.getDescription(),
            card.getPriority(),
            card.getUpdatedAt(),
            card.getCreatedAt());
    }
}