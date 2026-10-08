package com.mindconnect.infrastructure.ai.aiprovider.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateAiProviderRequest(
        @NotBlank
        @Size(max = 100)
        String name,

        @Size(max = 255)
        String legalName,

        String website,

        @NotNull
        boolean active) {
}