package com.mindconnect.domain.ai.aiprovider.model.valueobject;

import java.util.Objects;
import java.util.UUID;

public record AiProviderId(UUID value) {

    public AiProviderId {
        Objects.requireNonNull(value, "AiProviderId value must not be null");
    }

    public static AiProviderId generate() {
        return new AiProviderId(UUID.randomUUID());
    }
}