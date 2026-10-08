package com.mindconnect.domain.chat.chatescalationassignment.port.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.mindconnect.domain.chat.chatescalationassignment.model.aggregate.ChatEscalationAssignment;
import com.mindconnect.domain.chat.chatescalationassignment.model.valueobject.ChatEscalationAssignmentId;
import com.mindconnect.domain.chat.chatescalation.model.valueobject.ChatEscalationId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

public interface ChatEscalationAssignmentRepository {

    ChatEscalationAssignment save(ChatEscalationAssignment chatEscalationAssignment);

    Optional<ChatEscalationAssignment> findById(ChatEscalationAssignmentId id);

    List<ChatEscalationAssignment> findAll();

    void delete(ChatEscalationAssignment chatEscalationAssignment);

    boolean existsByEscalationIdAndProfessionalId(ChatEscalationId escalationId, ProfessionalId professionalId);
}