package com.mindconnect.application.chat.chatparticipant.usecase;

import com.mindconnect.application.chat.chatparticipant.dto.ChatParticipantResponse;
import com.mindconnect.application.chat.chatparticipant.exception.ChatParticipantNotFoundApplicationException;
import com.mindconnect.domain.chat.chatparticipant.model.valueobject.ChatParticipantId;
import com.mindconnect.domain.chat.chatparticipant.port.repository.ChatParticipantRepository;

public class GetChatParticipantByIdUseCase {

    private final ChatParticipantRepository participantRepository;

    public GetChatParticipantByIdUseCase(ChatParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public ChatParticipantResponse execute(ChatParticipantId id) {
        return participantRepository.findById(id)
                .map(ChatParticipantResponse::from)
                .orElseThrow(() -> new ChatParticipantNotFoundApplicationException(id));
    }
}