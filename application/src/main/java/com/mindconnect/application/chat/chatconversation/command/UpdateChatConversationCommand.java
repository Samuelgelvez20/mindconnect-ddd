package com.mindconnect.application.chat.chatconversation.command;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.chatconversationstatus.model.valueobject.ChatConversationStatusId;
import com.mindconnect.domain.chat.priority.model.valueobject.PriorityId;

public record UpdateChatConversationCommand(
        ChatConversationId id,
        ChatConversationStatusId conversationStatusId,
        PriorityId priorityId,
        Instant lastMessageAt,
        boolean closed,
        Instant closedAt) {

    public UpdateChatConversationCommand {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(conversationStatusId, "conversationStatusId must not be null");
        Objects.requireNonNull(priorityId, "priorityId must not be null");
    }
}