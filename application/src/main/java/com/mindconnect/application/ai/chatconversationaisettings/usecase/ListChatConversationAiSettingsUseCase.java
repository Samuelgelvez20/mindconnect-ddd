package com.mindconnect.application.ai.chatconversationaisettings.usecase;

import java.util.List;

import com.mindconnect.application.ai.chatconversationaisettings.dto.ChatConversationAiSettingsResponse;
import com.mindconnect.domain.ai.chatconversationaisettings.model.aggregate.ChatConversationAiSettings;
import com.mindconnect.domain.ai.chatconversationaisettings.port.repository.ChatConversationAiSettingsRepository;

public class ListChatConversationAiSettingsUseCase {

    private final ChatConversationAiSettingsRepository repository;

    public ListChatConversationAiSettingsUseCase(ChatConversationAiSettingsRepository repository) {
        this.repository = repository;
    }

    public List<ChatConversationAiSettingsResponse> execute() {
        List<ChatConversationAiSettings> settings = repository.findAll();
        return settings.stream()
                .map(ChatConversationAiSettingsResponse::from)
                .toList();
    }
}