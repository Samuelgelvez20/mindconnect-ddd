package com.mindconnect.infrastructure.ai.chatairunmetrics.adapters.in.rest.controllers;

import java.math.BigDecimal;
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

import com.mindconnect.application.ai.chatairunmetrics.command.RegisterChatAiRunMetricsCommand;
import com.mindconnect.application.ai.chatairunmetrics.command.UpdateChatAiRunMetricsCommand;
import com.mindconnect.application.ai.chatairunmetrics.dto.ChatAiRunMetricsResponse;
import com.mindconnect.application.ai.chatairunmetrics.usecase.DeleteChatAiRunMetricsUseCase;
import com.mindconnect.application.ai.chatairunmetrics.usecase.GetChatAiRunMetricsByIdUseCase;
import com.mindconnect.application.ai.chatairunmetrics.usecase.ListChatAiRunMetricsUseCase;
import com.mindconnect.application.ai.chatairunmetrics.usecase.RegisterChatAiRunMetricsUseCase;
import com.mindconnect.application.ai.chatairunmetrics.usecase.UpdateChatAiRunMetricsUseCase;
import com.mindconnect.infrastructure.ai.chatairunmetrics.adapters.in.rest.dtos.CreateChatAiRunMetricsRequest;
import com.mindconnect.infrastructure.ai.chatairunmetrics.adapters.in.rest.dtos.UpdateChatAiRunMetricsRequest;

@RestController
@RequestMapping("/api/chat-ai-run-metrics")
class ChatAiRunMetricsController {

    private final RegisterChatAiRunMetricsUseCase registerUseCase;
    private final GetChatAiRunMetricsByIdUseCase getByIdUseCase;
    private final ListChatAiRunMetricsUseCase listUseCase;
    private final UpdateChatAiRunMetricsUseCase updateUseCase;
    private final DeleteChatAiRunMetricsUseCase deleteUseCase;

    ChatAiRunMetricsController(
            RegisterChatAiRunMetricsUseCase registerUseCase,
            GetChatAiRunMetricsByIdUseCase getByIdUseCase,
            ListChatAiRunMetricsUseCase listUseCase,
            UpdateChatAiRunMetricsUseCase updateUseCase,
            DeleteChatAiRunMetricsUseCase deleteUseCase) {

        this.registerUseCase = registerUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    ResponseEntity<ChatAiRunMetricsResponse> create(@Valid @RequestBody CreateChatAiRunMetricsRequest request) {
        RegisterChatAiRunMetricsCommand command = new RegisterChatAiRunMetricsCommand(
                request.aiRunId(),
                request.promptTokens(),
                request.completionTokens(),
                request.totalTokens(),
                request.cost());
        ChatAiRunMetricsResponse response = registerUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    ResponseEntity<ChatAiRunMetricsResponse> getById(@PathVariable UUID id) {
        ChatAiRunMetricsResponse response = getByIdUseCase.execute(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    ResponseEntity<List<ChatAiRunMetricsResponse>> list() {
        List<ChatAiRunMetricsResponse> responses = listUseCase.execute();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    ResponseEntity<ChatAiRunMetricsResponse> update(@PathVariable UUID id, @Valid @RequestBody UpdateChatAiRunMetricsRequest request) {
        UpdateChatAiRunMetricsCommand command = new UpdateChatAiRunMetricsCommand(
                id,
                request.aiRunId(),
                request.promptTokens(),
                request.completionTokens(),
                request.totalTokens(),
                request.cost());
        ChatAiRunMetricsResponse response = updateUseCase.execute(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}