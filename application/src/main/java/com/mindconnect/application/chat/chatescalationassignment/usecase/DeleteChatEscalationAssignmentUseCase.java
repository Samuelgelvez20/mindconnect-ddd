package com.mindconnect.application.chat.chatescalationassignment.usecase;

import com.mindconnect.application.chat.chatescalationassignment.exception.ChatEscalationAssignmentNotFoundApplicationException;
import com.mindconnect.domain.chat.chatescalationassignment.model.valueobject.ChatEscalationAssignmentId;
import com.mindconnect.domain.chat.chatescalationassignment.port.repository.ChatEscalationAssignmentRepository;

public class DeleteChatEscalationAssignmentUseCase {

    private final ChatEscalationAssignmentRepository assignmentRepository;

    public DeleteChatEscalationAssignmentUseCase(ChatEscalationAssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    public void execute(ChatEscalationAssignmentId id) {

        var assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new ChatEscalationAssignmentNotFoundApplicationException(id));

        assignment.delete();
        assignmentRepository.delete(assignment);
    }
}