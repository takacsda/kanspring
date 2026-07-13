package com.takacsda.kanspring.card.web.dto;

import jakarta.validation.constraints.NotBlank;

public record ChangeDescriptionCardRequest(
    @NotBlank
    String description
) {}
