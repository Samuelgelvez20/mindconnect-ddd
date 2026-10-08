package com.mindconnect.domain.ai.chatairun.event;

import java.time.Instant;

import com.mindconnect.domain.ai.chatairun.model.valueobject.ChatAiRunId;
import com.mindconnect.domain.common.event.DomainEvent;

public record ChatAiRunDeletedEvent(
        ChatAiRunId chatAiRunId,
        Instant occurredOn) implements DomainEvent {
}