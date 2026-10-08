package com.mindconnect.domain.chat.chatmessage.event;

import java.time.Instant;
import java.util.Objects;

import com.mindconnect.domain.common.event.DomainEvent;
import com.mindconnect.domain.chat.chatmessage.model.valueobject.ChatMessageId;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.messagetype.model.valueobject.MessageTypeId;
import com.mindconnect.domain.chat.chatparticipant.model.valueobject.ChatParticipantId;

public record ChatMessageUpdatedEvent(
        ChatMessageId id,
        ChatConversationId conversationId,
        MessageTypeId messageTypeId,
        ChatParticipantId participantId,
        String content,
        Instant occurredOn) implements DomainEvent {

    public ChatMessageUpdatedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}