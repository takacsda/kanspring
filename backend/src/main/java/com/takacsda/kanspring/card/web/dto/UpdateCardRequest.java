package com.takacsda.kanspring.card.web.dto;

import java.util.UUID;

import com.takacsda.kanspring.card.domain.CardPriority;

public class UpdateCardRequest {
    private String title;
    private boolean titlePresent;

    private String description;
    private boolean descriptionPresent;

    private CardPriority priority;
    private boolean priorityPresent;

    private UUID assigneeId;
    private boolean assigneeIdPresent;

    public String getTitle() {return title;}
    public void setTitle(String title) {
        this.title = title;
        titlePresent = true;
    }
    public boolean hasTitle() {
        return titlePresent;
    }

    public String getDescription() {return description;}
    public void setDescription(String description) {
        this.description = description;
        descriptionPresent = true;
    }
    public boolean hasDescription() {
        return descriptionPresent;
    }

    public CardPriority getPriority() {return priority;}
    public void setPriority(CardPriority priority) {
        this.priority = priority;
        priorityPresent = true;
    }
    public boolean hasPriority() {
        return priorityPresent;
    }

    public UUID getAssigneeId() {return assigneeId;}
    public void setAssigneeId(UUID assigneeId) {
        this.assigneeId = assigneeId;
        assigneeIdPresent = true;
    }
    public boolean hasAssigneeId(){
        return assigneeIdPresent;
    }
}
