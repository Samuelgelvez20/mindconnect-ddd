package com.mindconnect.application.chat.chatescalationassignment.usecase;

import java.util.List;

import com.mindconnect.application.chat.chatescalationassignment.dto.ChatEscalationAssignmentResponse;
import com.mindconnect.domain.chat.chatescalationassignment.port.repository.ChatEscalationAssignmentRepository;

public class ListChatEscalationAssignmentUseCase {

    private final ChatEscalationAssignmentRepository assignmentRepository;

    public ListChatEscalationAssignmentUseCase(ChatEscalationAssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    public List<ChatEscalationAssignmentResponse> execute() {
        return assignmentRepository.findAll()
                .stream()
                .map(ChatEscalationAssignmentResponse::from)
                .toList();
    }
}