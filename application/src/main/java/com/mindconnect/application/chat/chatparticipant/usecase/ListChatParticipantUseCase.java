package com.mindconnect.application.chat.chatparticipant.usecase;

import java.util.List;

import com.mindconnect.application.chat.chatparticipant.dto.ChatParticipantResponse;
import com.mindconnect.domain.chat.chatparticipant.port.repository.ChatParticipantRepository;

public class ListChatParticipantUseCase {

    private final ChatParticipantRepository participantRepository;

    public ListChatParticipantUseCase(ChatParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public List<ChatParticipantResponse> execute() {
        return participantRepository.findAll()
                .stream()
                .map(ChatParticipantResponse::from)
                .toList();
    }
}