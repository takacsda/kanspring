package com.takacsda.kanspring.card.web.dto;

import java.util.UUID;

import com.takacsda.kanspring.card.domain.CardPriority;

public record CardRequest(
    String title,
    String description,
    CardPriority priority,
    UUID ownerId
) {}
