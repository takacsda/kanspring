package com.takacsda.kanspring.card.web.dto;

import java.util.UUID;

public record AssignCardRequest(
    UUID assigneeId
) {}
