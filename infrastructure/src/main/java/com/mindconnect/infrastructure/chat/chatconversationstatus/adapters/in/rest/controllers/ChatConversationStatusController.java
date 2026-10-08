package com.mindconnect.infrastructure.chat.chatconversationstatus.adapters.in.rest.controllers;

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

import com.mindconnect.application.chat.chatconversationstatus.command.RegisterChatConversationStatusCommand;
import com.mindconnect.application.chat.chatconversationstatus.command.UpdateChatConversationStatusCommand;
import com.mindconnect.application.chat.chatconversationstatus.dto.ChatConversationStatusResponse;
import com.mindconnect.application.chat.chatconversationstatus.usecase.DeleteChatConversationStatusUseCase;
import com.mindconnect.application.chat.chatconversationstatus.usecase.GetChatConversationStatusByIdUseCase;
import com.mindconnect.application.chat.chatconversationstatus.usecase.ListChatConversationStatusUseCase;
import com.mindconnect.application.chat.chatconversationstatus.usecase.RegisterChatConversationStatusUseCase;
import com.mindconnect.application.chat.chatconversationstatus.usecase.UpdateChatConversationStatusUseCase;
import com.mindconnect.domain.chat.chatconversationstatus.model.valueobject.ChatConversationStatusId;
import com.mindconnect.infrastructure.chat.chatconversationstatus.adapters.in.rest.dtos.CreateChatConversationStatusRequest;
import com.mindconnect.infrastructure.chat.chatconversationstatus.adapters.in.rest.dtos.UpdateChatConversationStatusRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/chat-conversation-statuses")
public class ChatConversationStatusController {

    private final RegisterChatConversationStatusUseCase registerUseCase;
    private final GetChatConversationStatusByIdUseCase getByIdUseCase;
    private final ListChatConversationStatusUseCase listUseCase;
    private final UpdateChatConversationStatusUseCase updateUseCase;
    private final DeleteChatConversationStatusUseCase deleteUseCase;

    public ChatConversationStatusController(
            RegisterChatConversationStatusUseCase registerUseCase,
            GetChatConversationStatusByIdUseCase getByIdUseCase,
            ListChatConversationStatusUseCase listUseCase,
            UpdateChatConversationStatusUseCase updateUseCase,
            DeleteChatConversationStatusUseCase deleteUseCase) {
        this.registerUseCase = registerUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    public ResponseEntity<ChatConversationStatusResponse> create(@Valid @RequestBody CreateChatConversationStatusRequest request) {
        var command = new RegisterChatConversationStatusCommand(request.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(registerUseCase.execute(command));
    }

    @GetMapping
    public ResponseEntity<List<ChatConversationStatusResponse>> findAll() {
        return ResponseEntity.ok(listUseCase.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatConversationStatusResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(getByIdUseCase.execute(new ChatConversationStatusId(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChatConversationStatusResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateChatConversationStatusRequest request) {

        var command = new UpdateChatConversationStatusCommand(
                new ChatConversationStatusId(id),
                request.name(),
                request.active());

        return ResponseEntity.ok(updateUseCase.execute(command));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteUseCase.execute(new ChatConversationStatusId(id));
        return ResponseEntity.noContent().build();
    }
}