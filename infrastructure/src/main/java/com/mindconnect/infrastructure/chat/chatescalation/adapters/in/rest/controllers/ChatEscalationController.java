package com.mindconnect.infrastructure.chat.chatescalation.adapters.in.rest.controllers;

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

import com.mindconnect.application.chat.chatescalation.command.RegisterChatEscalationCommand;
import com.mindconnect.application.chat.chatescalation.command.UpdateChatEscalationCommand;
import com.mindconnect.application.chat.chatescalation.dto.ChatEscalationResponse;
import com.mindconnect.application.chat.chatescalation.usecase.DeleteChatEscalationUseCase;
import com.mindconnect.application.chat.chatescalation.usecase.GetChatEscalationByIdUseCase;
import com.mindconnect.application.chat.chatescalation.usecase.ListChatEscalationUseCase;
import com.mindconnect.application.chat.chatescalation.usecase.RegisterChatEscalationUseCase;
import com.mindconnect.application.chat.chatescalation.usecase.UpdateChatEscalationUseCase;
import com.mindconnect.domain.chat.chatescalation.model.valueobject.ChatEscalationId;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.chatescalationstatus.model.valueobject.ChatEscalationStatusId;
import com.mindconnect.infrastructure.chat.chatescalation.adapters.in.rest.dtos.CreateChatEscalationRequest;
import com.mindconnect.infrastructure.chat.chatescalation.adapters.in.rest.dtos.UpdateChatEscalationRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/chat-escalations")
public class ChatEscalationController {

    private final RegisterChatEscalationUseCase registerUseCase;
    private final GetChatEscalationByIdUseCase getByIdUseCase;
    private final ListChatEscalationUseCase listUseCase;
    private final UpdateChatEscalationUseCase updateUseCase;
    private final DeleteChatEscalationUseCase deleteUseCase;

    public ChatEscalationController(
            RegisterChatEscalationUseCase registerUseCase,
            GetChatEscalationByIdUseCase getByIdUseCase,
            ListChatEscalationUseCase listUseCase,
            UpdateChatEscalationUseCase updateUseCase,
            DeleteChatEscalationUseCase deleteUseCase) {
        this.registerUseCase = registerUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    public ResponseEntity<ChatEscalationResponse> create(@Valid @RequestBody CreateChatEscalationRequest request) {
        var command = new RegisterChatEscalationCommand(
                new ChatConversationId(request.conversationId()),
                new ChatEscalationStatusId(request.statusId()),
                request.fromAi(),
                request.reason());
        return ResponseEntity.status(HttpStatus.CREATED).body(registerUseCase.execute(command));
    }

    @GetMapping
    public ResponseEntity<List<ChatEscalationResponse>> findAll() {
        return ResponseEntity.ok(listUseCase.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatEscalationResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(getByIdUseCase.execute(new ChatEscalationId(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChatEscalationResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateChatEscalationRequest request) {

        var command = new UpdateChatEscalationCommand(
                new ChatEscalationId(id),
                new ChatConversationId(request.conversationId()),
                new ChatEscalationStatusId(request.statusId()),
                request.fromAi(),
                request.reason());

        return ResponseEntity.ok(updateUseCase.execute(command));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteUseCase.execute(new ChatEscalationId(id));
        return ResponseEntity.noContent().build();
    }
}