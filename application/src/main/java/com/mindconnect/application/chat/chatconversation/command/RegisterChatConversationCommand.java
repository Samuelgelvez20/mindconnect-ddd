package com.mindconnect.application.chat.chatconversation.command;

import java.util.Objects;

import com.mindconnect.domain.chat.chatconversationstatus.model.valueobject.ChatConversationStatusId;
import com.mindconnect.domain.chat.priority.model.valueobject.PriorityId;

public record RegisterChatConversationCommand(
        ChatConversationStatusId conversationStatusId,
        PriorityId priorityId) {

    public RegisterChatConversationCommand {
        Objects.requireNonNull(conversationStatusId, "conversationStatusId must not be null");
        Objects.requireNonNull(priorityId, "priorityId must not be null");
    }
}