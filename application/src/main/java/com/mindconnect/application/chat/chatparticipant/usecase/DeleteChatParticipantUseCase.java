package com.mindconnect.application.chat.chatparticipant.usecase;

import com.mindconnect.application.chat.chatparticipant.exception.ChatParticipantNotFoundApplicationException;
import com.mindconnect.domain.chat.chatparticipant.model.valueobject.ChatParticipantId;
import com.mindconnect.domain.chat.chatparticipant.port.repository.ChatParticipantRepository;

public class DeleteChatParticipantUseCase {

    private final ChatParticipantRepository participantRepository;

    public DeleteChatParticipantUseCase(ChatParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public void execute(ChatParticipantId id) {

        var participant = participantRepository.findById(id)
                .orElseThrow(() -> new ChatParticipantNotFoundApplicationException(id));

        participant.delete();
        participantRepository.delete(participant);
    }
}