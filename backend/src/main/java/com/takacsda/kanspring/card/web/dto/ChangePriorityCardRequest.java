package com.takacsda.kanspring.card.web.dto;

import com.takacsda.kanspring.card.domain.CardPriority;

import jakarta.validation.constraints.NotNull;

public record ChangePriorityCardRequest(
    @NotNull
    CardPriority priority
) {}
