package com.mindconnect.domain.chat.chatparticipant.exception;

import com.mindconnect.domain.common.exception.DomainException;

public class InvalidChatParticipantException extends DomainException {

    public InvalidChatParticipantException(String message) {
        super(message);
    }
}