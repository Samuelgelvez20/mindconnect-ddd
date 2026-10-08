package com.mindconnect.infrastructure.ai.chatconversationaisettings.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record UpdateChatConversationAiSettingsRequest(
        @NotNull
        Boolean aiEnabled,

        @NotNull
        UUID defaultModelId) {
}