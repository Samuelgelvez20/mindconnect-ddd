package com.mindconnect.infrastructure.chat.messagetype.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateMessageTypeRequest(

        @NotBlank(message = "name is required")
        @Size(max = 100, message = "name must have at most 100 characters")
        String name,

        @NotNull(message = "active is required")
        Boolean active
) {
}