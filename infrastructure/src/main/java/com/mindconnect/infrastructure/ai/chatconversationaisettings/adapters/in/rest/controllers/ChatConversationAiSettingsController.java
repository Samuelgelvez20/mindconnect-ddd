package com.mindconnect.infrastructure.ai.chatconversationaisettings.adapters.in.rest.controllers;

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

import com.mindconnect.application.ai.chatconversationaisettings.command.RegisterChatConversationAiSettingsCommand;
import com.mindconnect.application.ai.chatconversationaisettings.command.UpdateChatConversationAiSettingsCommand;
import com.mindconnect.application.ai.chatconversationaisettings.dto.ChatConversationAiSettingsResponse;
import com.mindconnect.application.ai.chatconversationaisettings.usecase.DeleteChatConversationAiSettingsUseCase;
import com.mindconnect.application.ai.chatconversationaisettings.usecase.GetChatConversationAiSettingsByIdUseCase;
import com.mindconnect.application.ai.chatconversationaisettings.usecase.ListChatConversationAiSettingsUseCase;
import com.mindconnect.application.ai.chatconversationaisettings.usecase.RegisterChatConversationAiSettingsUseCase;
import com.mindconnect.application.ai.chatconversationaisettings.usecase.UpdateChatConversationAiSettingsUseCase;
import com.mindconnect.infrastructure.ai.chatconversationaisettings.adapters.in.rest.dtos.CreateChatConversationAiSettingsRequest;
import com.mindconnect.infrastructure.ai.chatconversationaisettings.adapters.in.rest.dtos.UpdateChatConversationAiSettingsRequest;

@RestController
@RequestMapping("/api/chat-conversation-ai-settings")
class ChatConversationAiSettingsController {

    private final RegisterChatConversationAiSettingsUseCase registerUseCase;
    private final GetChatConversationAiSettingsByIdUseCase getByIdUseCase;
    private final ListChatConversationAiSettingsUseCase listUseCase;
    private final UpdateChatConversationAiSettingsUseCase updateUseCase;
    private final DeleteChatConversationAiSettingsUseCase deleteUseCase;

    ChatConversationAiSettingsController(
            RegisterChatConversationAiSettingsUseCase registerUseCase,
            GetChatConversationAiSettingsByIdUseCase getByIdUseCase,
            ListChatConversationAiSettingsUseCase listUseCase,
            UpdateChatConversationAiSettingsUseCase updateUseCase,
            DeleteChatConversationAiSettingsUseCase deleteUseCase) {

        this.registerUseCase = registerUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    ResponseEntity<ChatConversationAiSettingsResponse> create(@Valid @RequestBody CreateChatConversationAiSettingsRequest request) {
        RegisterChatConversationAiSettingsCommand command = new RegisterChatConversationAiSettingsCommand(
                request.conversationId(),
                request.aiEnabled(),
                request.defaultModelId());
        ChatConversationAiSettingsResponse response = registerUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    ResponseEntity<ChatConversationAiSettingsResponse> getById(@PathVariable UUID id) {
        ChatConversationAiSettingsResponse response = getByIdUseCase.execute(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    ResponseEntity<List<ChatConversationAiSettingsResponse>> list() {
        List<ChatConversationAiSettingsResponse> responses = listUseCase.execute();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    ResponseEntity<ChatConversationAiSettingsResponse> update(@PathVariable UUID id, @Valid @RequestBody UpdateChatConversationAiSettingsRequest request) {
        UpdateChatConversationAiSettingsCommand command = new UpdateChatConversationAiSettingsCommand(
                id,
                request.aiEnabled(),
                request.defaultModelId());
        ChatConversationAiSettingsResponse response = updateUseCase.execute(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}