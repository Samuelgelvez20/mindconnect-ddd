package com.mindconnect.application.chat.chatconversation.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.chat.chatconversation.model.aggregate.ChatConversation;
import com.mindconnect.domain.chat.chatconversationstatus.model.valueobject.ChatConversationStatusId;
import com.mindconnect.domain.chat.priority.model.valueobject.PriorityId;

public record ChatConversationResponse(
        UUID id,
        UUID conversationStatusId,
        UUID priorityId,
        Instant lastMessageAt,
        boolean closed,
        Instant closedAt,
        UUID closedBy,
        Instant createdAt,
        Instant updatedAt) {

    public static ChatConversationResponse from(ChatConversation conversation) {
        return new ChatConversationResponse(
                conversation.id().value(),
                conversation.conversationStatusId().value(),
                conversation.priorityId().value(),
                conversation.lastMessageAt(),
                conversation.isClosed(),
                conversation.closedAt(),
                null,
                conversation.createdAt(),
                conversation.updatedAt());
    }
}