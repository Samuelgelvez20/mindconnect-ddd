package com.mindconnect.domain.ai.chatconversationaisettings.model.valueobject;

import java.util.Objects;
import java.util.UUID;

public record ChatConversationAiSettingsId(UUID value) {

    public ChatConversationAiSettingsId {
        Objects.requireNonNull(value, "ChatConversationAiSettingsId value must not be null");
    }

    public static ChatConversationAiSettingsId generate() {
        return new ChatConversationAiSettingsId(UUID.randomUUID());
    }
}