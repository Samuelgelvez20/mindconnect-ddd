package com.mindconnect.infrastructure.chat.chatescalationassignment.adapters.in.rest.controllers;

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

import com.mindconnect.application.chat.chatescalationassignment.command.RegisterChatEscalationAssignmentCommand;
import com.mindconnect.application.chat.chatescalationassignment.command.UpdateChatEscalationAssignmentCommand;
import com.mindconnect.application.chat.chatescalationassignment.dto.ChatEscalationAssignmentResponse;
import com.mindconnect.application.chat.chatescalationassignment.usecase.DeleteChatEscalationAssignmentUseCase;
import com.mindconnect.application.chat.chatescalationassignment.usecase.GetChatEscalationAssignmentByIdUseCase;
import com.mindconnect.application.chat.chatescalationassignment.usecase.ListChatEscalationAssignmentUseCase;
import com.mindconnect.application.chat.chatescalationassignment.usecase.RegisterChatEscalationAssignmentUseCase;
import com.mindconnect.application.chat.chatescalationassignment.usecase.UpdateChatEscalationAssignmentUseCase;
import com.mindconnect.domain.chat.chatescalationassignment.model.valueobject.ChatEscalationAssignmentId;
import com.mindconnect.domain.chat.chatescalation.model.valueobject.ChatEscalationId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.infrastructure.chat.chatescalationassignment.adapters.in.rest.dtos.CreateChatEscalationAssignmentRequest;
import com.mindconnect.infrastructure.chat.chatescalationassignment.adapters.in.rest.dtos.UpdateChatEscalationAssignmentRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/chat-escalation-assignments")
public class ChatEscalationAssignmentController {

    private final RegisterChatEscalationAssignmentUseCase registerUseCase;
    private final GetChatEscalationAssignmentByIdUseCase getByIdUseCase;
    private final ListChatEscalationAssignmentUseCase listUseCase;
    private final UpdateChatEscalationAssignmentUseCase updateUseCase;
    private final DeleteChatEscalationAssignmentUseCase deleteUseCase;

    public ChatEscalationAssignmentController(
            RegisterChatEscalationAssignmentUseCase registerUseCase,
            GetChatEscalationAssignmentByIdUseCase getByIdUseCase,
            ListChatEscalationAssignmentUseCase listUseCase,
            UpdateChatEscalationAssignmentUseCase updateUseCase,
            DeleteChatEscalationAssignmentUseCase deleteUseCase) {
        this.registerUseCase = registerUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    public ResponseEntity<ChatEscalationAssignmentResponse> create(@Valid @RequestBody CreateChatEscalationAssignmentRequest request) {
        var command = new RegisterChatEscalationAssignmentCommand(
                new ChatEscalationId(request.escalationId()),
                new ProfessionalId(request.professionalId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(registerUseCase.execute(command));
    }

    @GetMapping
    public ResponseEntity<List<ChatEscalationAssignmentResponse>> findAll() {
        return ResponseEntity.ok(listUseCase.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatEscalationAssignmentResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(getByIdUseCase.execute(new ChatEscalationAssignmentId(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChatEscalationAssignmentResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateChatEscalationAssignmentRequest request) {

        var command = new UpdateChatEscalationAssignmentCommand(
                new ChatEscalationAssignmentId(id),
                new ChatEscalationId(request.escalationId()),
                new ProfessionalId(request.professionalId()));

        return ResponseEntity.ok(updateUseCase.execute(command));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteUseCase.execute(new ChatEscalationAssignmentId(id));
        return ResponseEntity.noContent().build();
    }
}