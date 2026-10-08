package com.mindconnect.application.chat.chatconversationstatus.exception;

import com.mindconnect.application.common.exception.AlreadyExistsApplicationException;

public class ChatConversationStatusAlreadyExistsApplicationException extends AlreadyExistsApplicationException {

    public ChatConversationStatusAlreadyExistsApplicationException(String name) {
        super("ChatConversationStatus already exists with name: " + name);
    }
}