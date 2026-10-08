package com.mindconnect.infrastructure.ai.chatairunstatus.adapters.in.rest.controllers;

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

import com.mindconnect.application.ai.chatairunstatus.command.RegisterChatAiRunStatusCommand;
import com.mindconnect.application.ai.chatairunstatus.command.UpdateChatAiRunStatusCommand;
import com.mindconnect.application.ai.chatairunstatus.dto.ChatAiRunStatusResponse;
import com.mindconnect.application.ai.chatairunstatus.usecase.DeleteChatAiRunStatusUseCase;
import com.mindconnect.application.ai.chatairunstatus.usecase.GetChatAiRunStatusByIdUseCase;
import com.mindconnect.application.ai.chatairunstatus.usecase.ListChatAiRunStatusUseCase;
import com.mindconnect.application.ai.chatairunstatus.usecase.RegisterChatAiRunStatusUseCase;
import com.mindconnect.application.ai.chatairunstatus.usecase.UpdateChatAiRunStatusUseCase;
import com.mindconnect.infrastructure.ai.chatairunstatus.adapters.in.rest.dtos.CreateChatAiRunStatusRequest;
import com.mindconnect.infrastructure.ai.chatairunstatus.adapters.in.rest.dtos.UpdateChatAiRunStatusRequest;

@RestController
@RequestMapping("/api/chat-ai-run-statuses")
class ChatAiRunStatusController {

    private final RegisterChatAiRunStatusUseCase registerUseCase;
    private final GetChatAiRunStatusByIdUseCase getByIdUseCase;
    private final ListChatAiRunStatusUseCase listUseCase;
    private final UpdateChatAiRunStatusUseCase updateUseCase;
    private final DeleteChatAiRunStatusUseCase deleteUseCase;

    ChatAiRunStatusController(
            RegisterChatAiRunStatusUseCase registerUseCase,
            GetChatAiRunStatusByIdUseCase getByIdUseCase,
            ListChatAiRunStatusUseCase listUseCase,
            UpdateChatAiRunStatusUseCase updateUseCase,
            DeleteChatAiRunStatusUseCase deleteUseCase) {

        this.registerUseCase = registerUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    ResponseEntity<ChatAiRunStatusResponse> create(@Valid @RequestBody CreateChatAiRunStatusRequest request) {
        RegisterChatAiRunStatusCommand command = new RegisterChatAiRunStatusCommand(request.name());
        ChatAiRunStatusResponse response = registerUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    ResponseEntity<ChatAiRunStatusResponse> getById(@PathVariable UUID id) {
        ChatAiRunStatusResponse response = getByIdUseCase.execute(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    ResponseEntity<List<ChatAiRunStatusResponse>> list() {
        List<ChatAiRunStatusResponse> responses = listUseCase.execute();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    ResponseEntity<ChatAiRunStatusResponse> update(@PathVariable UUID id, @Valid @RequestBody UpdateChatAiRunStatusRequest request) {
        UpdateChatAiRunStatusCommand command = new UpdateChatAiRunStatusCommand(id, request.name());
        ChatAiRunStatusResponse response = updateUseCase.execute(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}