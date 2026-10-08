package com.mindconnect.application.ai.chatconversationaisettings.command;

import java.util.UUID;

public record RegisterChatConversationAiSettingsCommand(
        UUID conversationId,
        boolean aiEnabled,
        UUID defaultModelId) {
}