package com.mindconnect.application.chat.chatescalationassignment.usecase;

import com.mindconnect.application.chat.chatescalationassignment.dto.ChatEscalationAssignmentResponse;
import com.mindconnect.application.chat.chatescalationassignment.exception.ChatEscalationAssignmentNotFoundApplicationException;
import com.mindconnect.domain.chat.chatescalationassignment.model.valueobject.ChatEscalationAssignmentId;
import com.mindconnect.domain.chat.chatescalationassignment.port.repository.ChatEscalationAssignmentRepository;

public class GetChatEscalationAssignmentByIdUseCase {

    private final ChatEscalationAssignmentRepository assignmentRepository;

    public GetChatEscalationAssignmentByIdUseCase(ChatEscalationAssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    public ChatEscalationAssignmentResponse execute(ChatEscalationAssignmentId id) {
        return assignmentRepository.findById(id)
                .map(ChatEscalationAssignmentResponse::from)
                .orElseThrow(() -> new ChatEscalationAssignmentNotFoundApplicationException(id));
    }
}