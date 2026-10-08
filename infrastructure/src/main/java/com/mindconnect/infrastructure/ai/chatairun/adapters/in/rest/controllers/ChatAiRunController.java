package com.mindconnect.infrastructure.ai.chatairun.adapters.in.rest.controllers;

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

import com.mindconnect.application.ai.chatairun.command.RegisterChatAiRunCommand;
import com.mindconnect.application.ai.chatairun.command.UpdateChatAiRunCommand;
import com.mindconnect.application.ai.chatairun.dto.ChatAiRunResponse;
import com.mindconnect.application.ai.chatairun.usecase.DeleteChatAiRunUseCase;
import com.mindconnect.application.ai.chatairun.usecase.GetChatAiRunByIdUseCase;
import com.mindconnect.application.ai.chatairun.usecase.ListChatAiRunUseCase;
import com.mindconnect.application.ai.chatairun.usecase.RegisterChatAiRunUseCase;
import com.mindconnect.application.ai.chatairun.usecase.UpdateChatAiRunUseCase;
import com.mindconnect.infrastructure.ai.chatairun.adapters.in.rest.dtos.CreateChatAiRunRequest;
import com.mindconnect.infrastructure.ai.chatairun.adapters.in.rest.dtos.UpdateChatAiRunRequest;

@RestController
@RequestMapping("/api/chat-ai-runs")
class ChatAiRunController {

    private final RegisterChatAiRunUseCase registerUseCase;
    private final GetChatAiRunByIdUseCase getByIdUseCase;
    private final ListChatAiRunUseCase listUseCase;
    private final UpdateChatAiRunUseCase updateUseCase;
    private final DeleteChatAiRunUseCase deleteUseCase;

    ChatAiRunController(
            RegisterChatAiRunUseCase registerUseCase,
            GetChatAiRunByIdUseCase getByIdUseCase,
            ListChatAiRunUseCase listUseCase,
            UpdateChatAiRunUseCase updateUseCase,
            DeleteChatAiRunUseCase deleteUseCase) {

        this.registerUseCase = registerUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    ResponseEntity<ChatAiRunResponse> create(@Valid @RequestBody CreateChatAiRunRequest request) {
        RegisterChatAiRunCommand command = new RegisterChatAiRunCommand(
                request.conversationId(),
                request.messageId(),
                request.modelId(),
                request.aiRunStatusId());
        ChatAiRunResponse response = registerUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    ResponseEntity<ChatAiRunResponse> getById(@PathVariable UUID id) {
        ChatAiRunResponse response = getByIdUseCase.execute(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    ResponseEntity<List<ChatAiRunResponse>> list() {
        List<ChatAiRunResponse> responses = listUseCase.execute();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    ResponseEntity<ChatAiRunResponse> update(@PathVariable UUID id, @Valid @RequestBody UpdateChatAiRunRequest request) {
        UpdateChatAiRunCommand command = new UpdateChatAiRunCommand(
                id,
                request.conversationId(),
                request.messageId(),
                request.modelId(),
                request.aiRunStatusId());
        ChatAiRunResponse response = updateUseCase.execute(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}