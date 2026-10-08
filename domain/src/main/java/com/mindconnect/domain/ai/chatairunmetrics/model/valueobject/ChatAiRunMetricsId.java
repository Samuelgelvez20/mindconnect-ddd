package com.mindconnect.domain.ai.chatairunmetrics.model.valueobject;

import java.util.Objects;
import java.util.UUID;

public record ChatAiRunMetricsId(UUID value) {

    public ChatAiRunMetricsId {
        Objects.requireNonNull(value, "ChatAiRunMetricsId value must not be null");
    }

    public static ChatAiRunMetricsId generate() {
        return new ChatAiRunMetricsId(UUID.randomUUID());
    }
}