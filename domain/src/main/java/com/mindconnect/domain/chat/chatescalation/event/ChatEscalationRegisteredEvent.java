package com.mindconnect.domain.chat.chatescalation.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.chat.chatescalation.model.valueobject.ChatEscalationId;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.chatescalationstatus.model.valueobject.ChatEscalationStatusId;

public record ChatEscalationRegisteredEvent(
        ChatEscalationId id,
        ChatConversationId conversationId,
        ChatEscalationStatusId statusId,
        Instant occurredOn) implements DomainEvent {

    public ChatEscalationRegisteredEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}