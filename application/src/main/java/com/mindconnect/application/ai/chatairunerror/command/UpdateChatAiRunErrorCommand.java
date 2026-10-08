package com.mindconnect.application.ai.chatairunerror.command;

import java.util.UUID;

public record UpdateChatAiRunErrorCommand(
        UUID id,
        UUID aiRunId,
        String errorMessage,
        String errorCode,
        String providerErrorId) {
}