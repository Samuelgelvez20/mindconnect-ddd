package com.mindconnect.application.ai.chatconversationaisettings.usecase;

import java.util.UUID;

import com.mindconnect.application.ai.chatconversationaisettings.dto.ChatConversationAiSettingsResponse;
import com.mindconnect.application.ai.chatconversationaisettings.exception.ChatConversationAiSettingsNotFoundApplicationException;
import com.mindconnect.domain.ai.chatconversationaisettings.model.aggregate.ChatConversationAiSettings;
import com.mindconnect.domain.ai.chatconversationaisettings.model.valueobject.ChatConversationAiSettingsId;
import com.mindconnect.domain.ai.chatconversationaisettings.port.repository.ChatConversationAiSettingsRepository;

public class GetChatConversationAiSettingsByIdUseCase {

    private final ChatConversationAiSettingsRepository repository;

    public GetChatConversationAiSettingsByIdUseCase(ChatConversationAiSettingsRepository repository) {
        this.repository = repository;
    }

    public ChatConversationAiSettingsResponse execute(UUID id) {
        ChatConversationAiSettingsId settingsId = new ChatConversationAiSettingsId(id);
        ChatConversationAiSettings settings = repository.findById(settingsId)
                .orElseThrow(() -> new ChatConversationAiSettingsNotFoundApplicationException(id.toString()));
        return ChatConversationAiSettingsResponse.from(settings);
    }
}