package com.takacsda.kanspring.card.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.takacsda.kanspring.card.application.CardService;
import com.takacsda.kanspring.card.domain.Card;
import com.takacsda.kanspring.card.web.dto.CardRequest;
import com.takacsda.kanspring.card.web.dto.AssignCardRequest;
import com.takacsda.kanspring.card.web.dto.CardResponse;
import com.takacsda.kanspring.card.web.dto.ChangeDescriptionCardRequest;
import com.takacsda.kanspring.card.web.dto.ChangePriorityCardRequest;
import com.takacsda.kanspring.card.web.dto.ChangeTitleCardRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api/cards")
public class CardController {
    private final CardService cardService;
    
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("")
    public List<CardResponse> getAllCards() {
        return cardService.findAll().
                stream().
                map(CardResponse::from).
                toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardResponse> getCardById(@PathVariable UUID id) {
        return cardService.findById(id)
                .map(CardResponse::from)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("")
    public ResponseEntity<CardResponse> addCard(@RequestBody CardRequest cardRequest) {
        Card card = new Card(cardRequest.title(), cardRequest.description(), cardRequest.priority(),cardRequest.ownerId());
        cardService.save(card);
        URI location = URI.create("/api/cards/" + card.getId());

        return ResponseEntity.
                created(location).
                body(CardResponse.from(card));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable UUID id) {
        if (!cardService.deleteBId(id)) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/assignee")
    public ResponseEntity<CardResponse> assignToUser(@PathVariable UUID id, @RequestBody AssignCardRequest request) {
        return cardService.assignToUser(id, request.assigneeId()).
                map(CardResponse::from).
                map(ResponseEntity::ok).
                orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/title")
    public ResponseEntity<CardResponse> changeTitle(@PathVariable UUID id, @RequestBody ChangeTitleCardRequest request) {
        return cardService.changeTitle(id, request.title()).
                map(CardResponse::from).
                map(ResponseEntity::ok).
                orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/description")
    public ResponseEntity<CardResponse> changeDescription(@PathVariable UUID id, @RequestBody ChangeDescriptionCardRequest request) {
        return cardService.changeDescription(id, request.description()).
                map(CardResponse::from).
                map(ResponseEntity::ok).
                orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/priority")
    public ResponseEntity<CardResponse> changePriority(@PathVariable UUID id, @RequestBody ChangePriorityCardRequest request) {
        return cardService.changePriority(id, request.priority()).
                map(CardResponse::from).
                map(ResponseEntity::ok).
                orElseGet(() -> ResponseEntity.notFound().build());
    }
    
}
