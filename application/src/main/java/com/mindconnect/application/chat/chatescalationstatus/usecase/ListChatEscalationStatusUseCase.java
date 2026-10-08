package com.mindconnect.application.chat.chatescalationstatus.usecase;

import java.util.List;

import com.mindconnect.application.chat.chatescalationstatus.dto.ChatEscalationStatusResponse;
import com.mindconnect.domain.chat.chatescalationstatus.port.repository.ChatEscalationStatusRepository;

public class ListChatEscalationStatusUseCase {

    private final ChatEscalationStatusRepository statusRepository;

    public ListChatEscalationStatusUseCase(ChatEscalationStatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public List<ChatEscalationStatusResponse> execute() {
        return statusRepository.findAll()
                .stream()
                .map(ChatEscalationStatusResponse::from)
                .toList();
    }
}