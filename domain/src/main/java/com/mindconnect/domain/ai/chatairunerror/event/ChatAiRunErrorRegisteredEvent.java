package com.mindconnect.domain.ai.chatairunerror.event;

import java.time.Instant;

import com.mindconnect.domain.ai.chatairunerror.model.valueobject.ChatAiRunErrorId;
import com.mindconnect.domain.common.event.DomainEvent;

public record ChatAiRunErrorRegisteredEvent(
        ChatAiRunErrorId chatAiRunErrorId,
        Instant occurredOn) implements DomainEvent {
}