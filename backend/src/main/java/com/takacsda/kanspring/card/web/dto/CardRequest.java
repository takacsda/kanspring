package com.takacsda.kanspring.card.web.dto;

import java.util.UUID;

import com.takacsda.kanspring.card.domain.CardPriority;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CardRequest(
    @NotBlank
    String title,
    String description,
    CardPriority priority,
    @NotNull
    UUID ownerId,
    UUID assigneeId
) {}
