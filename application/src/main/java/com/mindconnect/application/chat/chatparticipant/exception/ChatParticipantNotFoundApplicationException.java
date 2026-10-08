package com.mindconnect.application.chat.chatparticipant.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.chat.chatparticipant.model.valueobject.ChatParticipantId;

public class ChatParticipantNotFoundApplicationException extends NotFoundApplicationException {

    public ChatParticipantNotFoundApplicationException(ChatParticipantId id) {
        super("ChatParticipant not found with id: " + id.value());
    }
}