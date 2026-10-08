package com.mindconnect.domain.chat.chatescalationstatus.model.valueobject;

import java.util.Objects;
import java.util.UUID;

public record ChatEscalationStatusId(UUID value) {

    public ChatEscalationStatusId {
        Objects.requireNonNull(value, "ChatEscalationStatusId value must not be null");
    }

    public static ChatEscalationStatusId generate() {
        return new ChatEscalationStatusId(UUID.randomUUID());
    }
}