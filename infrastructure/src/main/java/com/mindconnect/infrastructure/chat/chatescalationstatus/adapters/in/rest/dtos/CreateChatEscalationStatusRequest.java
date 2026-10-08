package com.mindconnect.infrastructure.chat.chatescalationstatus.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateChatEscalationStatusRequest(

        @NotBlank(message = "name is required")
        @Size(max = 50, message = "name must have at most 50 characters")
        String name
) {
}