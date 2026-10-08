package com.mindconnect.infrastructure.ai.aimodel.adapters.in.rest.controllers;

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

import com.mindconnect.application.ai.aimodel.command.RegisterAiModelCommand;
import com.mindconnect.application.ai.aimodel.command.UpdateAiModelCommand;
import com.mindconnect.application.ai.aimodel.dto.AiModelResponse;
import com.mindconnect.application.ai.aimodel.usecase.DeleteAiModelUseCase;
import com.mindconnect.application.ai.aimodel.usecase.GetAiModelByIdUseCase;
import com.mindconnect.application.ai.aimodel.usecase.ListAiModelUseCase;
import com.mindconnect.application.ai.aimodel.usecase.RegisterAiModelUseCase;
import com.mindconnect.application.ai.aimodel.usecase.UpdateAiModelUseCase;
import com.mindconnect.infrastructure.ai.aimodel.adapters.in.rest.dtos.CreateAiModelRequest;
import com.mindconnect.infrastructure.ai.aimodel.adapters.in.rest.dtos.UpdateAiModelRequest;

@RestController
@RequestMapping("/api/ai-models")
class AiModelController {

    private final RegisterAiModelUseCase registerUseCase;
    private final GetAiModelByIdUseCase getByIdUseCase;
    private final ListAiModelUseCase listUseCase;
    private final UpdateAiModelUseCase updateUseCase;
    private final DeleteAiModelUseCase deleteUseCase;

    AiModelController(
            RegisterAiModelUseCase registerUseCase,
            GetAiModelByIdUseCase getByIdUseCase,
            ListAiModelUseCase listUseCase,
            UpdateAiModelUseCase updateUseCase,
            DeleteAiModelUseCase deleteUseCase) {

        this.registerUseCase = registerUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    ResponseEntity<AiModelResponse> create(@Valid @RequestBody CreateAiModelRequest request) {
        RegisterAiModelCommand command = new RegisterAiModelCommand(
                request.aiProviderId(),
                request.name(),
                request.modelKey(),
                request.inputTokenPrice(),
                request.outputTokenPrice(),
                request.maxTokens(),
                request.contextWindow());
        AiModelResponse response = registerUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    ResponseEntity<AiModelResponse> getById(@PathVariable UUID id) {
        AiModelResponse response = getByIdUseCase.execute(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    ResponseEntity<List<AiModelResponse>> list() {
        List<AiModelResponse> responses = listUseCase.execute();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    ResponseEntity<AiModelResponse> update(@PathVariable UUID id, @Valid @RequestBody UpdateAiModelRequest request) {
        UpdateAiModelCommand command = new UpdateAiModelCommand(
                id,
                request.aiProviderId(),
                request.name(),
                request.modelKey(),
                request.inputTokenPrice(),
                request.outputTokenPrice(),
                request.maxTokens(),
                request.contextWindow(),
                request.active());
        AiModelResponse response = updateUseCase.execute(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}