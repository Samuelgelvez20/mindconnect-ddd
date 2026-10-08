package com.mindconnect.domain.ai.chatairunstatus.model.valueobject;

import java.util.Objects;
import java.util.UUID;

public record ChatAiRunStatusId(UUID value) {

    public ChatAiRunStatusId {
        Objects.requireNonNull(value, "ChatAiRunStatusId value must not be null");
    }

    public static ChatAiRunStatusId generate() {
        return new ChatAiRunStatusId(UUID.randomUUID());
    }
}