package com.mindconnect.domain.chat.chatescalationassignment.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.chat.chatescalationassignment.model.valueobject.ChatEscalationAssignmentId;
import com.mindconnect.domain.chat.chatescalation.model.valueobject.ChatEscalationId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

public record ChatEscalationAssignmentUpdatedEvent(
        ChatEscalationAssignmentId id,
        ChatEscalationId escalationId,
        ProfessionalId professionalId,
        Instant occurredOn) implements DomainEvent {

    public ChatEscalationAssignmentUpdatedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}