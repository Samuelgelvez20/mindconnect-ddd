package com.mindconnect.domain.ai.chatconversationaisettings.event;

import java.time.Instant;

import com.mindconnect.domain.ai.chatconversationaisettings.model.valueobject.ChatConversationAiSettingsId;
import com.mindconnect.domain.common.event.DomainEvent;

public record ChatConversationAiSettingsDeletedEvent(
        ChatConversationAiSettingsId chatConversationAiSettingsId,
        Instant occurredOn) implements DomainEvent {
}