package com.mindconnect.infrastructure.ai.chatairunerror.adapters.in.rest.controllers;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

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

import com.mindconnect.application.ai.chatairunerror.command.RegisterChatAiRunErrorCommand;
import com.mindconnect.application.ai.chatairunerror.command.UpdateChatAiRunErrorCommand;
import com.mindconnect.application.ai.chatairunerror.dto.ChatAiRunErrorResponse;
import com.mindconnect.application.ai.chatairunerror.usecase.DeleteChatAiRunErrorUseCase;
import com.mindconnect.application.ai.chatairunerror.usecase.GetChatAiRunErrorByIdUseCase;
import com.mindconnect.application.ai.chatairunerror.usecase.ListChatAiRunErrorUseCase;
import com.mindconnect.application.ai.chatairunerror.usecase.RegisterChatAiRunErrorUseCase;
import com.mindconnect.application.ai.chatairunerror.usecase.UpdateChatAiRunErrorUseCase;
import com.mindconnect.infrastructure.ai.chatairunerror.adapters.in.rest.dtos.CreateChatAiRunErrorRequest;
import com.mindconnect.infrastructure.ai.chatairunerror.adapters.in.rest.dtos.UpdateChatAiRunErrorRequest;

@RestController
@RequestMapping("/api/chat-ai-run-errors")
class ChatAiRunErrorController {

    private final RegisterChatAiRunErrorUseCase registerUseCase;
    private final GetChatAiRunErrorByIdUseCase getByIdUseCase;
    private final ListChatAiRunErrorUseCase listUseCase;
    private final UpdateChatAiRunErrorUseCase updateUseCase;
    private final DeleteChatAiRunErrorUseCase deleteUseCase;

    ChatAiRunErrorController(
            RegisterChatAiRunErrorUseCase registerUseCase,
            GetChatAiRunErrorByIdUseCase getByIdUseCase,
            ListChatAiRunErrorUseCase listUseCase,
            UpdateChatAiRunErrorUseCase updateUseCase,
            DeleteChatAiRunErrorUseCase deleteUseCase) {

        this.registerUseCase = registerUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    ResponseEntity<ChatAiRunErrorResponse> create(@Valid @RequestBody CreateChatAiRunErrorRequest request) {
        RegisterChatAiRunErrorCommand command = new RegisterChatAiRunErrorCommand(
                request.aiRunId(),
                request.errorMessage(),
                request.errorCode(),
                request.providerErrorId());
        ChatAiRunErrorResponse response = registerUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    ResponseEntity<ChatAiRunErrorResponse> getById(@PathVariable UUID id) {
        ChatAiRunErrorResponse response = getByIdUseCase.execute(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    ResponseEntity<List<ChatAiRunErrorResponse>> list() {
        List<ChatAiRunErrorResponse> responses = listUseCase.execute();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    ResponseEntity<ChatAiRunErrorResponse> update(@PathVariable UUID id, @Valid @RequestBody UpdateChatAiRunErrorRequest request) {
        UpdateChatAiRunErrorCommand command = new UpdateChatAiRunErrorCommand(
                id,
                request.aiRunId(),
                request.errorMessage(),
                request.errorCode(),
                request.providerErrorId());
        ChatAiRunErrorResponse response = updateUseCase.execute(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}