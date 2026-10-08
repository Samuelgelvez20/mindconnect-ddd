package com.mindconnect.domain.ai.chatconversationaisettings.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.ai.chatconversationaisettings.event.ChatConversationAiSettingsDeletedEvent;
import com.mindconnect.domain.ai.chatconversationaisettings.event.ChatConversationAiSettingsRegisteredEvent;
import com.mindconnect.domain.ai.chatconversationaisettings.event.ChatConversationAiSettingsUpdatedEvent;
import com.mindconnect.domain.ai.chatconversationaisettings.exception.InvalidChatConversationAiSettingsException;
import com.mindconnect.domain.ai.chatconversationaisettings.model.valueobject.ChatConversationAiSettingsId;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.ai.aimodel.model.valueobject.AiModelId;

public class ChatConversationAiSettings extends AggregateRoot {

    private final ChatConversationAiSettingsId id;
    private ChatConversationId conversationId;
    private boolean aiEnabled;
    private AiModelId defaultModelId;
    private final Instant createdAt;
    private Instant updatedAt;

    private ChatConversationAiSettings(
            ChatConversationAiSettingsId id,
            ChatConversationId conversationId,
            boolean aiEnabled,
            AiModelId defaultModelId,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.conversationId = conversationId;
        this.aiEnabled = aiEnabled;
        this.defaultModelId = defaultModelId;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updateUpdatedAt(Objects.requireNonNull(updatedAt, "updatedAt must not be null"));
    }

    public static ChatConversationAiSettings register(
            ChatConversationId conversationId,
            boolean aiEnabled,
            AiModelId defaultModelId) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        ChatConversationAiSettingsId id = ChatConversationAiSettingsId.generate();

        if (conversationId == null) {
            throw new InvalidChatConversationAiSettingsException("conversationId must not be null");
        }
        if (defaultModelId == null) {
            throw new InvalidChatConversationAiSettingsException("defaultModelId must not be null");
        }

        ChatConversationAiSettings settings = new ChatConversationAiSettings(
                id,
                conversationId,
                aiEnabled,
                defaultModelId,
                now,
                now);

        settings.recordEvent(new ChatConversationAiSettingsRegisteredEvent(id, now));
        return settings;
    }

    public static ChatConversationAiSettings restore(
            ChatConversationAiSettingsId id,
            ChatConversationId conversationId,
            boolean aiEnabled,
            AiModelId defaultModelId,
            Instant createdAt,
            Instant updatedAt) {

        return new ChatConversationAiSettings(id, conversationId, aiEnabled, defaultModelId, createdAt, updatedAt);
    }

    public void update(
            boolean aiEnabled,
            AiModelId defaultModelId) {

        if (defaultModelId == null) {
            throw new InvalidChatConversationAiSettingsException("defaultModelId must not be null");
        }

        this.aiEnabled = aiEnabled;
        this.defaultModelId = defaultModelId;
        this.updateUpdatedAt(Instant.now().truncatedTo(ChronoUnit.MICROS));

        recordEvent(new ChatConversationAiSettingsUpdatedEvent(this.id, this.aiEnabled, this.updatedAt));
    }

    public void delete() {
        recordEvent(new ChatConversationAiSettingsDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public ChatConversationAiSettingsId id() {
        return id;
    }

    public ChatConversationId conversationId() {
        return conversationId;
    }

    public boolean aiEnabled() {
        return aiEnabled;
    }

    public AiModelId defaultModelId() {
        return defaultModelId;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    private void updateUpdatedAt(Instant updatedAt) {
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }
}