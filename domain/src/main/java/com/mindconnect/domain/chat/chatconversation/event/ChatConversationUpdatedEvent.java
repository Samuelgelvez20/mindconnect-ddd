package com.mindconnect.domain.chat.chatconversation.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.chatconversationstatus.model.valueobject.ChatConversationStatusId;
import com.mindconnect.domain.chat.priority.model.valueobject.PriorityId;

public record ChatConversationUpdatedEvent(
        ChatConversationId id,
        ChatConversationStatusId conversationStatusId,
        PriorityId priorityId,
        Instant lastMessageAt,
        boolean closed,
        Instant closedAt,
        Instant updatedAt,
        Instant occurredOn) implements DomainEvent {

    public ChatConversationUpdatedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}