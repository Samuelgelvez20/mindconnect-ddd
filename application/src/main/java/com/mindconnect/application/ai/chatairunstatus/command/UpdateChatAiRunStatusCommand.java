package com.mindconnect.application.ai.chatairunstatus.command;

import java.util.UUID;

public record UpdateChatAiRunStatusCommand(
        UUID id,
        String name) {
}