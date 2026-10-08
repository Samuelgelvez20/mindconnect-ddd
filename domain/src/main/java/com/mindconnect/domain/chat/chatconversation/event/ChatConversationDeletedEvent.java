package com.mindconnect.domain.chat.chatconversation.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;

public record ChatConversationDeletedEvent(ChatConversationId id, Instant occurredOn) implements DomainEvent {

    public ChatConversationDeletedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}