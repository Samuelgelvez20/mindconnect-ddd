package com.mindconnect.domain.chat.chatmessage.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.chat.chatmessage.event.ChatMessageDeletedEvent;
import com.mindconnect.domain.chat.chatmessage.event.ChatMessageRegisteredEvent;
import com.mindconnect.domain.chat.chatmessage.event.ChatMessageUpdatedEvent;
import com.mindconnect.domain.chat.chatmessage.exception.InvalidChatMessageException;
import com.mindconnect.domain.chat.chatmessage.model.valueobject.ChatMessageId;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.messagetype.model.valueobject.MessageTypeId;
import com.mindconnect.domain.chat.chatparticipant.model.valueobject.ChatParticipantId;

public class ChatMessage extends AggregateRoot {

    private final ChatMessageId id;
    private ChatConversationId conversationId;
    private MessageTypeId messageTypeId;
    private ChatParticipantId participantId;
    private String content;
    private String metadata;
    private final Instant createdAt;
    private Instant updatedAt;

    private ChatMessage(
            ChatMessageId id,
            ChatConversationId conversationId,
            MessageTypeId messageTypeId,
            ChatParticipantId participantId,
            String content,
            String metadata,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.conversationId = conversationId;
        this.messageTypeId = messageTypeId;
        this.participantId = participantId;
        this.content = content;
        this.metadata = metadata;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static ChatMessage register(
            ChatConversationId conversationId,
            MessageTypeId messageTypeId,
            ChatParticipantId participantId,
            String content,
            String metadata) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        ChatMessageId id = ChatMessageId.generate();

        if (conversationId == null) {
            throw new InvalidChatMessageException("conversationId must not be null");
        }
        if (messageTypeId == null) {
            throw new InvalidChatMessageException("messageTypeId must not be null");
        }
        if (participantId == null) {
            throw new InvalidChatMessageException("participantId must not be null");
        }
        if (content == null || content.isBlank()) {
            throw new InvalidChatMessageException("content must not be blank");
        }

        ChatMessage message = new ChatMessage(
                id,
                conversationId,
                messageTypeId,
                participantId,
                content.trim(),
                metadata != null ? metadata.trim() : null,
                now,
                now);

        message.recordEvent(new ChatMessageRegisteredEvent(id, now));
        return message;
    }

    public static ChatMessage restore(
            ChatMessageId id,
            ChatConversationId conversationId,
            MessageTypeId messageTypeId,
            ChatParticipantId participantId,
            String content,
            String metadata,
            Instant createdAt,
            Instant updatedAt) {

        return new ChatMessage(id, conversationId, messageTypeId, participantId, content, metadata, createdAt, updatedAt);
    }

    public void update(
            ChatConversationId conversationId,
            MessageTypeId messageTypeId,
            ChatParticipantId participantId,
            String content,
            String metadata) {

        this.conversationId = Objects.requireNonNull(conversationId, "conversationId must not be null");
        this.messageTypeId = Objects.requireNonNull(messageTypeId, "messageTypeId must not be null");
        this.participantId = Objects.requireNonNull(participantId, "participantId must not be null");
        this.content = content != null ? content.trim() : null;
        this.metadata = metadata != null ? metadata.trim() : null;
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new ChatMessageUpdatedEvent(this.id, this.conversationId, this.messageTypeId, this.participantId, this.content, this.updatedAt));
    }

    public void delete() {
        recordEvent(new ChatMessageDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public ChatMessageId id() {
        return id;
    }

    public ChatConversationId conversationId() {
        return conversationId;
    }

    public MessageTypeId messageTypeId() {
        return messageTypeId;
    }

    public ChatParticipantId participantId() {
        return participantId;
    }

    public String content() {
        return content;
    }

    public String metadata() {
        return metadata;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }
}