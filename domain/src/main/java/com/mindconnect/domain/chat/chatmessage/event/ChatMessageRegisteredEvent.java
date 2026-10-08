package com.mindconnect.domain.chat.chatmessage.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.chat.chatmessage.model.valueobject.ChatMessageId;

public record ChatMessageRegisteredEvent(ChatMessageId id, Instant occurredOn) implements DomainEvent {

    public ChatMessageRegisteredEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}