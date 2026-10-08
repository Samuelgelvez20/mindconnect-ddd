package com.mindconnect.domain.ai.chatairunerror.event;

import java.time.Instant;

import com.mindconnect.domain.ai.chatairunerror.model.valueobject.ChatAiRunErrorId;
import com.mindconnect.domain.ai.chatairun.model.valueobject.ChatAiRunId;
import com.mindconnect.domain.common.event.DomainEvent;

public record ChatAiRunErrorUpdatedEvent(
        ChatAiRunErrorId chatAiRunErrorId,
        ChatAiRunId aiRunId,
        String errorMessage,
        String errorCode,
        String providerErrorId,
        Instant createdAt) implements DomainEvent {

    @Override
    public Instant occurredOn() {
        return createdAt;
    }
}