package com.mindconnect.application.chat.chatescalationassignment.usecase;

import com.mindconnect.application.chat.chatescalationassignment.command.UpdateChatEscalationAssignmentCommand;
import com.mindconnect.application.chat.chatescalationassignment.dto.ChatEscalationAssignmentResponse;
import com.mindconnect.application.chat.chatescalationassignment.exception.ChatEscalationAssignmentAlreadyExistsApplicationException;
import com.mindconnect.application.chat.chatescalationassignment.exception.ChatEscalationAssignmentNotFoundApplicationException;
import com.mindconnect.domain.chat.chatescalationassignment.model.valueobject.ChatEscalationAssignmentId;
import com.mindconnect.domain.chat.chatescalationassignment.port.repository.ChatEscalationAssignmentRepository;
import com.mindconnect.domain.chat.chatescalation.model.valueobject.ChatEscalationId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

public class UpdateChatEscalationAssignmentUseCase {

    private final ChatEscalationAssignmentRepository assignmentRepository;

    public UpdateChatEscalationAssignmentUseCase(ChatEscalationAssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    public ChatEscalationAssignmentResponse execute(UpdateChatEscalationAssignmentCommand command) {

        var assignment = assignmentRepository.findById(command.id())
                .orElseThrow(() -> new ChatEscalationAssignmentNotFoundApplicationException(command.id()));

        assignment.update(
                command.escalationId(),
                command.professionalId());

        if (assignmentRepository.existsByEscalationIdAndProfessionalId(
                assignment.escalationId(), assignment.professionalId())) {
            throw new ChatEscalationAssignmentAlreadyExistsApplicationException(
                    assignment.escalationId().value(), assignment.professionalId().value());
        }

        return ChatEscalationAssignmentResponse.from(assignmentRepository.save(assignment));
    }
}