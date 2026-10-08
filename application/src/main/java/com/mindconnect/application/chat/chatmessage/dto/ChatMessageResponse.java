package com.mindconnect.application.chat.chatmessage.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.chat.chatmessage.model.aggregate.ChatMessage;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.messagetype.model.valueobject.MessageTypeId;
import com.mindconnect.domain.chat.chatparticipant.model.valueobject.ChatParticipantId;

public record ChatMessageResponse(
        UUID id,
        UUID conversationId,
        UUID messageTypeId,
        UUID participantId,
        String content,
        String metadata,
        Instant createdAt,
        Instant updatedAt) {

    public static ChatMessageResponse from(ChatMessage message) {
        return new ChatMessageResponse(
                message.id().value(),
                message.conversationId().value(),
                message.messageTypeId().value(),
                message.participantId().value(),
                message.content(),
                message.metadata(),
                message.createdAt(),
                message.updatedAt());
    }
}