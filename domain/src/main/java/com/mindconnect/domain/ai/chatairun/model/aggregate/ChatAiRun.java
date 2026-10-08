package com.mindconnect.domain.ai.chatairun.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.ai.chatairun.event.ChatAiRunDeletedEvent;
import com.mindconnect.domain.ai.chatairun.event.ChatAiRunRegisteredEvent;
import com.mindconnect.domain.ai.chatairun.event.ChatAiRunUpdatedEvent;
import com.mindconnect.domain.ai.chatairun.exception.InvalidChatAiRunException;
import com.mindconnect.domain.ai.chatairun.model.valueobject.ChatAiRunId;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.chatmessage.model.valueobject.ChatMessageId;
import com.mindconnect.domain.ai.aimodel.model.valueobject.AiModelId;
import com.mindconnect.domain.ai.chatairunstatus.model.valueobject.ChatAiRunStatusId;

public class ChatAiRun extends AggregateRoot {

    private final ChatAiRunId id;
    private ChatConversationId conversationId;
    private ChatMessageId messageId;
    private AiModelId modelId;
    private ChatAiRunStatusId aiRunStatusId;
    private final Instant createdAt;
    private Instant updatedAt;

    private ChatAiRun(
            ChatAiRunId id,
            ChatConversationId conversationId,
            ChatMessageId messageId,
            AiModelId modelId,
            ChatAiRunStatusId aiRunStatusId,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.conversationId = conversationId;
        this.messageId = messageId;
        this.modelId = modelId;
        this.aiRunStatusId = aiRunStatusId;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static ChatAiRun register(
            ChatConversationId conversationId,
            ChatMessageId messageId,
            AiModelId modelId,
            ChatAiRunStatusId aiRunStatusId) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        ChatAiRunId id = ChatAiRunId.generate();

        if (conversationId == null) {
            throw new InvalidChatAiRunException("conversationId must not be null");
        }
        if (messageId == null) {
            throw new InvalidChatAiRunException("messageId must not be null");
        }
        if (modelId == null) {
            throw new InvalidChatAiRunException("modelId must not be null");
        }
        if (aiRunStatusId == null) {
            throw new InvalidChatAiRunException("aiRunStatusId must not be null");
        }

        ChatAiRun run = new ChatAiRun(
                id,
                conversationId,
                messageId,
                modelId,
                aiRunStatusId,
                now,
                now);

        run.recordEvent(new ChatAiRunRegisteredEvent(id, now));
        return run;
    }

    public static ChatAiRun restore(
            ChatAiRunId id,
            ChatConversationId conversationId,
            ChatMessageId messageId,
            AiModelId modelId,
            ChatAiRunStatusId aiRunStatusId,
            Instant createdAt,
            Instant updatedAt) {

        return new ChatAiRun(id, conversationId, messageId, modelId, aiRunStatusId, createdAt, updatedAt);
    }

    public void update(
            ChatConversationId conversationId,
            ChatMessageId messageId,
            AiModelId modelId,
            ChatAiRunStatusId aiRunStatusId) {

        if (conversationId == null) {
            throw new InvalidChatAiRunException("conversationId must not be null");
        }
        if (messageId == null) {
            throw new InvalidChatAiRunException("messageId must not be null");
        }
        if (modelId == null) {
            throw new InvalidChatAiRunException("modelId must not be null");
        }
        if (aiRunStatusId == null) {
            throw new InvalidChatAiRunException("aiRunStatusId must not be null");
        }

        this.conversationId = conversationId;
        this.messageId = messageId;
        this.modelId = modelId;
        this.aiRunStatusId = aiRunStatusId;
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new ChatAiRunUpdatedEvent(this.id, this.aiRunStatusId, this.updatedAt));
    }

    public void delete() {
        recordEvent(new ChatAiRunDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public ChatAiRunId id() {
        return id;
    }

    public ChatConversationId conversationId() {
        return conversationId;
    }

    public ChatMessageId messageId() {
        return messageId;
    }

    public AiModelId modelId() {
        return modelId;
    }

    public ChatAiRunStatusId aiRunStatusId() {
        return aiRunStatusId;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }
}