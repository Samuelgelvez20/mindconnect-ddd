package com.mindconnect.application.ai.chatconversationaisettings.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;

public class ChatConversationAiSettingsNotFoundApplicationException extends NotFoundApplicationException {

    public ChatConversationAiSettingsNotFoundApplicationException(String id) {
        super("ChatConversationAiSettings not found with id: " + id);
    }
}