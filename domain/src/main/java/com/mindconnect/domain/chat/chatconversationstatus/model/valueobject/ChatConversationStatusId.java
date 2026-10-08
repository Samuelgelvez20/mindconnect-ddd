package com.mindconnect.domain.chat.chatconversationstatus.model.valueobject;

import java.util.Objects;
import java.util.UUID;

public record ChatConversationStatusId(UUID value) {

    public ChatConversationStatusId {
        Objects.requireNonNull(value, "ChatConversationStatusId value must not be null");
    }

    public static ChatConversationStatusId generate() {
        return new ChatConversationStatusId(UUID.randomUUID());
    }
}