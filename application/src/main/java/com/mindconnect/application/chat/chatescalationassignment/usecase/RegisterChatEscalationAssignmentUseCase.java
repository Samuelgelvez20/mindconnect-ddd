package com.mindconnect.application.chat.chatescalationassignment.usecase;

import com.mindconnect.application.chat.chatescalationassignment.command.RegisterChatEscalationAssignmentCommand;
import com.mindconnect.application.chat.chatescalationassignment.dto.ChatEscalationAssignmentResponse;
import com.mindconnect.application.chat.chatescalationassignment.exception.ChatEscalationAssignmentAlreadyExistsApplicationException;
import com.mindconnect.domain.chat.chatescalationassignment.model.aggregate.ChatEscalationAssignment;
import com.mindconnect.domain.chat.chatescalationassignment.port.repository.ChatEscalationAssignmentRepository;
import com.mindconnect.domain.chat.chatescalation.model.valueobject.ChatEscalationId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

public class RegisterChatEscalationAssignmentUseCase {

    private final ChatEscalationAssignmentRepository assignmentRepository;

    public RegisterChatEscalationAssignmentUseCase(ChatEscalationAssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    public ChatEscalationAssignmentResponse execute(RegisterChatEscalationAssignmentCommand command) {

        ChatEscalationAssignment assignment = ChatEscalationAssignment.register(
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