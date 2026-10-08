package com.mindconnect.domain.ai.chatairun.event;

import java.time.Instant;

import com.mindconnect.domain.ai.chatairun.model.valueobject.ChatAiRunId;
import com.mindconnect.domain.ai.chatairunstatus.model.valueobject.ChatAiRunStatusId;
import com.mindconnect.domain.common.event.DomainEvent;

public record ChatAiRunUpdatedEvent(
        ChatAiRunId chatAiRunId,
        ChatAiRunStatusId aiRunStatusId,
        Instant updatedAt) implements DomainEvent {

    @Override
    public Instant occurredOn() {
        return updatedAt;
    }
}