package com.mindconnect.domain.chat.chatescalationassignment.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.chat.chatescalationassignment.event.ChatEscalationAssignmentDeletedEvent;
import com.mindconnect.domain.chat.chatescalationassignment.event.ChatEscalationAssignmentRegisteredEvent;
import com.mindconnect.domain.chat.chatescalationassignment.event.ChatEscalationAssignmentUpdatedEvent;
import com.mindconnect.domain.chat.chatescalationassignment.exception.InvalidChatEscalationAssignmentException;
import com.mindconnect.domain.chat.chatescalationassignment.model.valueobject.ChatEscalationAssignmentId;
import com.mindconnect.domain.chat.chatescalation.model.valueobject.ChatEscalationId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

public class ChatEscalationAssignment extends AggregateRoot {

    private final ChatEscalationAssignmentId id;
    private ChatEscalationId escalationId;
    private ProfessionalId professionalId;
    private final Instant assignedAt;
    private Instant updatedAt;

    private ChatEscalationAssignment(
            ChatEscalationAssignmentId id,
            ChatEscalationId escalationId,
            ProfessionalId professionalId,
            Instant assignedAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.escalationId = escalationId;
        this.professionalId = professionalId;
        this.assignedAt = Objects.requireNonNull(assignedAt, "assignedAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static ChatEscalationAssignment register(
            ChatEscalationId escalationId,
            ProfessionalId professionalId) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        ChatEscalationAssignmentId id = ChatEscalationAssignmentId.generate();

        if (escalationId == null) {
            throw new InvalidChatEscalationAssignmentException("escalationId must not be null");
        }
        if (professionalId == null) {
            throw new InvalidChatEscalationAssignmentException("professionalId must not be null");
        }

        ChatEscalationAssignment assignment = new ChatEscalationAssignment(
                id,
                escalationId,
                professionalId,
                now,
                now);

        assignment.recordEvent(new ChatEscalationAssignmentRegisteredEvent(id, now));
        return assignment;
    }

    public static ChatEscalationAssignment restore(
            ChatEscalationAssignmentId id,
            ChatEscalationId escalationId,
            ProfessionalId professionalId,
            Instant assignedAt,
            Instant updatedAt) {

        return new ChatEscalationAssignment(id, escalationId, professionalId, assignedAt, updatedAt);
    }

    public void update(
            ChatEscalationId escalationId,
            ProfessionalId professionalId) {

        this.escalationId = Objects.requireNonNull(escalationId, "escalationId must not be null");
        this.professionalId = Objects.requireNonNull(professionalId, "professionalId must not be null");
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new ChatEscalationAssignmentUpdatedEvent(this.id, this.escalationId, this.professionalId, this.updatedAt));
    }

    public void delete() {
        recordEvent(new ChatEscalationAssignmentDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public ChatEscalationAssignmentId id() {
        return id;
    }

    public ChatEscalationId escalationId() {
        return escalationId;
    }

    public ProfessionalId professionalId() {
        return professionalId;
    }

    public Instant assignedAt() {
        return assignedAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }
}