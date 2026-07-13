package com.takacsda.kanspring.card.web.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record AssignCardRequest(
    @NotNull
    UUID assigneeId
) {}
