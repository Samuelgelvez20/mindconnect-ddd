package com.mindconnect.application.chat.chatescalation.usecase;

import java.util.List;

import com.mindconnect.application.chat.chatescalation.dto.ChatEscalationResponse;
import com.mindconnect.domain.chat.chatescalation.port.repository.ChatEscalationRepository;

public class ListChatEscalationUseCase {

    private final ChatEscalationRepository escalationRepository;

    public ListChatEscalationUseCase(ChatEscalationRepository escalationRepository) {
        this.escalationRepository = escalationRepository;
    }

    public List<ChatEscalationResponse> execute() {
        return escalationRepository.findAll()
                .stream()
                .map(ChatEscalationResponse::from)
                .toList();
    }
}