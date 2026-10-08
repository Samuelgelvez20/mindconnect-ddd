package com.mindconnect.infrastructure.ai.chatairunstatus.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateChatAiRunStatusRequest(
        @NotBlank
        @Size(max = 50)
        String name) {
}