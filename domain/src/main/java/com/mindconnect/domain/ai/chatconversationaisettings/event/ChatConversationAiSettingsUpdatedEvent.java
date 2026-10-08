package com.mindconnect.domain.ai.chatconversationaisettings.event;

import java.time.Instant;

import com.mindconnect.domain.ai.chatconversationaisettings.model.valueobject.ChatConversationAiSettingsId;
import com.mindconnect.domain.common.event.DomainEvent;

public record ChatConversationAiSettingsUpdatedEvent(
        ChatConversationAiSettingsId chatConversationAiSettingsId,
        boolean aiEnabled,
        Instant updatedAt) implements DomainEvent {

    @Override
    public Instant occurredOn() {
        return updatedAt;
    }
}