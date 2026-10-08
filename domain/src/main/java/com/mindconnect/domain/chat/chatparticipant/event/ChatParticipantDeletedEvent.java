package com.mindconnect.domain.chat.chatparticipant.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.chat.chatparticipant.model.valueobject.ChatParticipantId;

public record ChatParticipantDeletedEvent(ChatParticipantId id, Instant occurredOn) implements DomainEvent {

    public ChatParticipantDeletedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}