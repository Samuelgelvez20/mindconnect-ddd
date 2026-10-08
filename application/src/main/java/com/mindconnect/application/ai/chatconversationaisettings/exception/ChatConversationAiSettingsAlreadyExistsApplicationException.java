package com.mindconnect.application.ai.chatconversationaisettings.exception;

import com.mindconnect.application.common.exception.AlreadyExistsApplicationException;

public class ChatConversationAiSettingsAlreadyExistsApplicationException extends AlreadyExistsApplicationException {

    public ChatConversationAiSettingsAlreadyExistsApplicationException(String conversationId) {
        super("ChatConversationAiSettings already exists for conversation: " + conversationId);
    }
}