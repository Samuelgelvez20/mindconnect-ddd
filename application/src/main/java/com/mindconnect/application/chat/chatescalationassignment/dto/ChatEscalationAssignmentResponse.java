package com.mindconnect.application.chat.chatescalationassignment.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.chat.chatescalationassignment.model.aggregate.ChatEscalationAssignment;
import com.mindconnect.domain.chat.chatescalation.model.valueobject.ChatEscalationId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

public record ChatEscalationAssignmentResponse(
        UUID id,
        UUID escalationId,
        UUID professionalId,
        Instant assignedAt,
        Instant updatedAt) {

    public static ChatEscalationAssignmentResponse from(ChatEscalationAssignment assignment) {
        return new ChatEscalationAssignmentResponse(
                assignment.id().value(),
                assignment.escalationId().value(),
                assignment.professionalId().value(),
                assignment.assignedAt(),
                assignment.updatedAt());
    }
}