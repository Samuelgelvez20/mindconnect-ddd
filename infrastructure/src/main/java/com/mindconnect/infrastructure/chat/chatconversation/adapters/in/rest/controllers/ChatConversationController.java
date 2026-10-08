package com.mindconnect.infrastructure.chat.chatconversation.adapters.in.rest.controllers;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mindconnect.application.chat.chatconversation.command.RegisterChatConversationCommand;
import com.mindconnect.application.chat.chatconversation.command.UpdateChatConversationCommand;
import com.mindconnect.application.chat.chatconversation.dto.ChatConversationResponse;
import com.mindconnect.application.chat.chatconversation.usecase.DeleteChatConversationUseCase;
import com.mindconnect.application.chat.chatconversation.usecase.GetChatConversationByIdUseCase;
import com.mindconnect.application.chat.chatconversation.usecase.ListChatConversationUseCase;
import com.mindconnect.application.chat.chatconversation.usecase.RegisterChatConversationUseCase;
import com.mindconnect.application.chat.chatconversation.usecase.UpdateChatConversationUseCase;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.chatconversationstatus.model.valueobject.ChatConversationStatusId;
import com.mindconnect.domain.chat.priority.model.valueobject.PriorityId;
import com.mindconnect.infrastructure.chat.chatconversation.adapters.in.rest.dtos.CreateChatConversationRequest;
import com.mindconnect.infrastructure.chat.chatconversation.adapters.in.rest.dtos.UpdateChatConversationRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/chat-conversations")
public class ChatConversationController {

    private final RegisterChatConversationUseCase registerUseCase;
    private final GetChatConversationByIdUseCase getByIdUseCase;
    private final ListChatConversationUseCase listUseCase;
    private final UpdateChatConversationUseCase updateUseCase;
    private final DeleteChatConversationUseCase deleteUseCase;

    public ChatConversationController(
            RegisterChatConversationUseCase registerUseCase,
            GetChatConversationByIdUseCase getByIdUseCase,
            ListChatConversationUseCase listUseCase,
            UpdateChatConversationUseCase updateUseCase,
            DeleteChatConversationUseCase deleteUseCase) {
        this.registerUseCase = registerUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    public ResponseEntity<ChatConversationResponse> create(@Valid @RequestBody CreateChatConversationRequest request) {
        var command = new RegisterChatConversationCommand(
                new ChatConversationStatusId(request.conversationStatusId()),
                new PriorityId(request.priorityId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(registerUseCase.execute(command));
    }

    @GetMapping
    public ResponseEntity<List<ChatConversationResponse>> findAll() {
        return ResponseEntity.ok(listUseCase.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatConversationResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(getByIdUseCase.execute(new ChatConversationId(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChatConversationResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateChatConversationRequest request) {

        var command = new UpdateChatConversationCommand(
                new ChatConversationId(id),
                new ChatConversationStatusId(request.conversationStatusId()),
                new PriorityId(request.priorityId()),
                request.lastMessageAt(),
                request.closed(),
                request.closedAt());

        return ResponseEntity.ok(updateUseCase.execute(command));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteUseCase.execute(new ChatConversationId(id));
        return ResponseEntity.noContent().build();
    }
}