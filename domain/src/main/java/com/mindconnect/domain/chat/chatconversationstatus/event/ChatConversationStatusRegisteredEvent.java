package com.mindconnect.domain.chat.chatconversationstatus.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.chat.chatconversationstatus.model.valueobject.ChatConversationStatusId;

public record ChatConversationStatusRegisteredEvent(ChatConversationStatusId id, Instant occurredOn) implements DomainEvent {

    public ChatConversationStatusRegisteredEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}