package com.mindconnect.application.chat.chatmessage.command;

import java.util.Objects;

import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.messagetype.model.valueobject.MessageTypeId;
import com.mindconnect.domain.chat.chatparticipant.model.valueobject.ChatParticipantId;

public record RegisterChatMessageCommand(
        ChatConversationId conversationId,
        MessageTypeId messageTypeId,
        ChatParticipantId participantId,
        String content,
        String metadata) {

    public RegisterChatMessageCommand {
        Objects.requireNonNull(conversationId, "conversationId must not be null");
        Objects.requireNonNull(messageTypeId, "messageTypeId must not be null");
        Objects.requireNonNull(participantId, "participantId must not be null");
        Objects.requireNonNull(content, "content must not be null");
    }
}