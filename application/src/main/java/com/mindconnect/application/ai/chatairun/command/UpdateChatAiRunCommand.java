package com.mindconnect.application.ai.chatairun.command;

import java.util.UUID;

public record UpdateChatAiRunCommand(
        UUID id,
        UUID conversationId,
        UUID messageId,
        UUID modelId,
        UUID aiRunStatusId) {
}