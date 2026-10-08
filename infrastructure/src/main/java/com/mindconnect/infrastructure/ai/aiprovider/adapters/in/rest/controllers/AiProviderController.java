package com.mindconnect.infrastructure.ai.aiprovider.adapters.in.rest.controllers;

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

import com.mindconnect.application.ai.aiprovider.command.RegisterAiProviderCommand;
import com.mindconnect.application.ai.aiprovider.command.UpdateAiProviderCommand;
import com.mindconnect.application.ai.aiprovider.dto.AiProviderResponse;
import com.mindconnect.application.ai.aiprovider.usecase.DeleteAiProviderUseCase;
import com.mindconnect.application.ai.aiprovider.usecase.GetAiProviderByIdUseCase;
import com.mindconnect.application.ai.aiprovider.usecase.ListAiProviderUseCase;
import com.mindconnect.application.ai.aiprovider.usecase.RegisterAiProviderUseCase;
import com.mindconnect.application.ai.aiprovider.usecase.UpdateAiProviderUseCase;
import com.mindconnect.infrastructure.ai.aiprovider.adapters.in.rest.dtos.CreateAiProviderRequest;
import com.mindconnect.infrastructure.ai.aiprovider.adapters.in.rest.dtos.UpdateAiProviderRequest;

@RestController
@RequestMapping("/api/ai-providers")
class AiProviderController {

    private final RegisterAiProviderUseCase registerUseCase;
    private final GetAiProviderByIdUseCase getByIdUseCase;
    private final ListAiProviderUseCase listUseCase;
    private final UpdateAiProviderUseCase updateUseCase;
    private final DeleteAiProviderUseCase deleteUseCase;

    AiProviderController(
            RegisterAiProviderUseCase registerUseCase,
            GetAiProviderByIdUseCase getByIdUseCase,
            ListAiProviderUseCase listUseCase,
            UpdateAiProviderUseCase updateUseCase,
            DeleteAiProviderUseCase deleteUseCase) {

        this.registerUseCase = registerUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    ResponseEntity<AiProviderResponse> create(@Valid @RequestBody CreateAiProviderRequest request) {
        RegisterAiProviderCommand command = new RegisterAiProviderCommand(
                request.name(),
                request.legalName(),
                request.website());
        AiProviderResponse response = registerUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    ResponseEntity<AiProviderResponse> getById(@PathVariable UUID id) {
        AiProviderResponse response = getByIdUseCase.execute(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    ResponseEntity<List<AiProviderResponse>> list() {
        List<AiProviderResponse> responses = listUseCase.execute();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    ResponseEntity<AiProviderResponse> update(@PathVariable UUID id, @Valid @RequestBody UpdateAiProviderRequest request) {
        UpdateAiProviderCommand command = new UpdateAiProviderCommand(
                id,
                request.name(),
                request.legalName(),
                request.website(),
                request.active());
        AiProviderResponse response = updateUseCase.execute(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}