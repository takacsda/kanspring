package com.takacsda.kanspring.card.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.takacsda.kanspring.card.application.CardService;
import com.takacsda.kanspring.card.domain.Card;
import com.takacsda.kanspring.card.web.dto.CreateCardRequest;
import com.takacsda.kanspring.card.web.dto.UpdateCardRequest;
import com.takacsda.kanspring.card.web.dto.CardResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

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
@RequestMapping("/api/v1/cards")
public class CardController {
    private final CardService cardService;
    
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @Operation(summary = "List all the cards")
    @ApiResponse(responseCode = "200", description = "All cards listed")
    @GetMapping("")
    public List<CardResponse> getAllCards() {
        return cardService.findAll().
                stream().
                map(CardResponse::from).
                toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a specific card")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Card found"),
        @ApiResponse(responseCode = "404", description = "Card with UUID NOT FOUND", content = @Content)
    })
    public ResponseEntity<CardResponse> getCardById(@PathVariable UUID id) {
        return cardService.findById(id)
                .map(CardResponse::from)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("")
    @Operation(summary = "Create a new card")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "New card is created"),
        @ApiResponse(responseCode = "400", description = "Invalid request for creating card", content = @Content)
    })
    public ResponseEntity<CardResponse> addCard(@Valid @RequestBody CreateCardRequest cardRequest) {
        Card card = cardService.createCard(cardRequest.title(), cardRequest.description(),cardRequest.priority(),cardRequest.ownerId(), cardRequest.assigneeId());
        URI location = URI.create("/api/cards/" + card.getId());

        return ResponseEntity.
                created(location).
                body(CardResponse.from(card));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update a card")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Card updated"),
        @ApiResponse(responseCode = "400", description = "Invalid request for updating card", content = @Content),
        @ApiResponse(responseCode = "404", description = "Card not found for update", content = @Content),
    })
    public ResponseEntity<CardResponse> updateCard(@PathVariable UUID id, @Valid @RequestBody UpdateCardRequest request) {
        return cardService.updateCard(id, request).
            map(CardResponse::from).
            map(ResponseEntity::ok).
            orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a card")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Card deleted"),
        @ApiResponse(responseCode = "404", description = "Card NOT FOUND")
    })
    public ResponseEntity<Void> deleteCard(@PathVariable UUID id) {
        if (!cardService.deleteById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.noContent().build();
    }
}
