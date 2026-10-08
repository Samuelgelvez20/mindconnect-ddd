package com.mindconnect.application.ai.aiprovider.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.ai.aiprovider.model.aggregate.AiProvider;
import com.mindconnect.domain.ai.aiprovider.model.valueobject.AiProviderId;

public record AiProviderResponse(
        UUID id,
        String name,
        String legalName,
        String website,
        boolean active,
        Instant createdAt,
        Instant updatedAt) {

    public static AiProviderResponse from(AiProvider provider) {
        return new AiProviderResponse(
                provider.id().value(),
                provider.name(),
                provider.legalName(),
                provider.website(),
                provider.active(),
                provider.createdAt(),
                provider.updatedAt());
    }
}