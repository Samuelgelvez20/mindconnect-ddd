package com.mindconnect.application.ai.chatconversationaisettings.command;

import java.util.UUID;

public record UpdateChatConversationAiSettingsCommand(
        UUID id,
        boolean aiEnabled,
        UUID defaultModelId) {
}