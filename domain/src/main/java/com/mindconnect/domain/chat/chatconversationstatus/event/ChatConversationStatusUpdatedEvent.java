package com.mindconnect.domain.chat.chatconversationstatus.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.chat.chatconversationstatus.model.valueobject.ChatConversationStatusId;

public record ChatConversationStatusUpdatedEvent(ChatConversationStatusId id, String name, Instant occurredOn) implements DomainEvent {

    public ChatConversationStatusUpdatedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}