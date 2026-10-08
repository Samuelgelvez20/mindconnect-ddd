package com.mindconnect.domain.ai.chatairunstatus.event;

import java.time.Instant;

import com.mindconnect.domain.ai.chatairunstatus.model.valueobject.ChatAiRunStatusId;
import com.mindconnect.domain.common.event.DomainEvent;

public record ChatAiRunStatusRegisteredEvent(
        ChatAiRunStatusId chatAiRunStatusId,
        Instant occurredOn) implements DomainEvent {
}