package com.mindconnect.application.ai.chatconversationaisettings.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.ai.chatconversationaisettings.model.aggregate.ChatConversationAiSettings;
import com.mindconnect.domain.ai.chatconversationaisettings.model.valueobject.ChatConversationAiSettingsId;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.ai.aimodel.model.valueobject.AiModelId;

public record ChatConversationAiSettingsResponse(
        UUID id,
        UUID conversationId,
        boolean aiEnabled,
        UUID defaultModelId,
        Instant createdAt,
        Instant updatedAt) {

    public static ChatConversationAiSettingsResponse from(ChatConversationAiSettings settings) {
        return new ChatConversationAiSettingsResponse(
                settings.id().value(),
                settings.conversationId().value(),
                settings.aiEnabled(),
                settings.defaultModelId().value(),
                settings.createdAt(),
                settings.updatedAt());
    }
}