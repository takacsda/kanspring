package com.takacsda.kanspring.card.web.dto;

import com.takacsda.kanspring.card.domain.CardPriority;

public record ChangePriorityCardRequest(
    CardPriority priority
) {}
