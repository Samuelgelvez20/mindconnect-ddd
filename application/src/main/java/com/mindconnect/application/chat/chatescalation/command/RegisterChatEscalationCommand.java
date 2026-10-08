package com.mindconnect.application.chat.chatescalation.command;

import java.util.Objects;

import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.chatescalationstatus.model.valueobject.ChatEscalationStatusId;

public record RegisterChatEscalationCommand(
        ChatConversationId conversationId,
        ChatEscalationStatusId statusId,
        boolean fromAi,
        String reason) {

    public RegisterChatEscalationCommand {
        Objects.requireNonNull(conversationId, "conversationId must not be null");
        Objects.requireNonNull(statusId, "statusId must not be null");
    }
}