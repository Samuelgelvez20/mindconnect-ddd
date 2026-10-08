package com.mindconnect.domain.ai.chatairunstatus.event;

import java.time.Instant;

import com.mindconnect.domain.ai.chatairunstatus.model.valueobject.ChatAiRunStatusId;
import com.mindconnect.domain.common.event.DomainEvent;

public record ChatAiRunStatusUpdatedEvent(
        ChatAiRunStatusId chatAiRunStatusId,
        String name,
        Instant updatedAt) implements DomainEvent {

    @Override
    public Instant occurredOn() {
        return updatedAt;
    }
}