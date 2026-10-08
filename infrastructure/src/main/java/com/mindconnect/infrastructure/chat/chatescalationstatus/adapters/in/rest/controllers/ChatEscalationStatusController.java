package com.mindconnect.infrastructure.chat.chatescalationstatus.adapters.in.rest.controllers;

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

import com.mindconnect.application.chat.chatescalationstatus.command.RegisterChatEscalationStatusCommand;
import com.mindconnect.application.chat.chatescalationstatus.command.UpdateChatEscalationStatusCommand;
import com.mindconnect.application.chat.chatescalationstatus.dto.ChatEscalationStatusResponse;
import com.mindconnect.application.chat.chatescalationstatus.usecase.DeleteChatEscalationStatusUseCase;
import com.mindconnect.application.chat.chatescalationstatus.usecase.GetChatEscalationStatusByIdUseCase;
import com.mindconnect.application.chat.chatescalationstatus.usecase.ListChatEscalationStatusUseCase;
import com.mindconnect.application.chat.chatescalationstatus.usecase.RegisterChatEscalationStatusUseCase;
import com.mindconnect.application.chat.chatescalationstatus.usecase.UpdateChatEscalationStatusUseCase;
import com.mindconnect.domain.chat.chatescalationstatus.model.valueobject.ChatEscalationStatusId;
import com.mindconnect.infrastructure.chat.chatescalationstatus.adapters.in.rest.dtos.CreateChatEscalationStatusRequest;
import com.mindconnect.infrastructure.chat.chatescalationstatus.adapters.in.rest.dtos.UpdateChatEscalationStatusRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/chat-escalation-statuses")
public class ChatEscalationStatusController {

    private final RegisterChatEscalationStatusUseCase registerUseCase;
    private final GetChatEscalationStatusByIdUseCase getByIdUseCase;
    private final ListChatEscalationStatusUseCase listUseCase;
    private final UpdateChatEscalationStatusUseCase updateUseCase;
    private final DeleteChatEscalationStatusUseCase deleteUseCase;

    public ChatEscalationStatusController(
            RegisterChatEscalationStatusUseCase registerUseCase,
            GetChatEscalationStatusByIdUseCase getByIdUseCase,
            ListChatEscalationStatusUseCase listUseCase,
            UpdateChatEscalationStatusUseCase updateUseCase,
            DeleteChatEscalationStatusUseCase deleteUseCase) {
        this.registerUseCase = registerUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    public ResponseEntity<ChatEscalationStatusResponse> create(@Valid @RequestBody CreateChatEscalationStatusRequest request) {
        var command = new RegisterChatEscalationStatusCommand(request.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(registerUseCase.execute(command));
    }

    @GetMapping
    public ResponseEntity<List<ChatEscalationStatusResponse>> findAll() {
        return ResponseEntity.ok(listUseCase.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatEscalationStatusResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(getByIdUseCase.execute(new ChatEscalationStatusId(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChatEscalationStatusResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateChatEscalationStatusRequest request) {

        var command = new UpdateChatEscalationStatusCommand(
                new ChatEscalationStatusId(id),
                request.name(),
                request.active());

        return ResponseEntity.ok(updateUseCase.execute(command));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteUseCase.execute(new ChatEscalationStatusId(id));
        return ResponseEntity.noContent().build();
    }
}