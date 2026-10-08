package com.mindconnect.domain.ai.chatconversationaisettings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mindconnect.domain.ai.chatconversationaisettings.event.ChatConversationAiSettingsDeletedEvent;
import com.mindconnect.domain.ai.chatconversationaisettings.event.ChatConversationAiSettingsRegisteredEvent;
import com.mindconnect.domain.ai.chatconversationaisettings.event.ChatConversationAiSettingsUpdatedEvent;
import com.mindconnect.domain.ai.chatconversationaisettings.exception.InvalidChatConversationAiSettingsException;
import com.mindconnect.domain.ai.chatconversationaisettings.model.aggregate.ChatConversationAiSettings;
import com.mindconnect.domain.ai.chatconversationaisettings.model.valueobject.ChatConversationAiSettingsId;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.ai.aimodel.model.valueobject.AiModelId;

class ChatConversationAiSettingsTest {

    private final UUID conversationId = UUID.randomUUID();
    private final UUID modelId = UUID.randomUUID();

    @Test
    void register_ValidData_CreatesSettingsAndRecordsEvent() {
        ChatConversationAiSettings settings = ChatConversationAiSettings.register(
                new ChatConversationId(conversationId),
                true,
                new AiModelId(modelId));

        assertNotNull(settings.id());
        assertEquals(new ChatConversationId(conversationId), settings.conversationId());
        assertEquals(true, settings.aiEnabled());
        assertEquals(new AiModelId(modelId), settings.defaultModelId());
        assertNotNull(settings.createdAt());
        assertNotNull(settings.updatedAt());

        List<com.mindconnect.domain.common.event.DomainEvent> events = settings.domainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof ChatConversationAiSettingsRegisteredEvent);
        ChatConversationAiSettingsRegisteredEvent event = (ChatConversationAiSettingsRegisteredEvent) events.get(0);
        assertEquals(settings.id(), event.chatConversationAiSettingsId());
    }

    @Test
    void register_NullConversationId_ThrowsException() {
        InvalidChatConversationAiSettingsException ex = assertThrows(InvalidChatConversationAiSettingsException.class,
                () -> ChatConversationAiSettings.register(null, true, new AiModelId(modelId)));
        assertEquals("conversationId must not be null", ex.getMessage());
    }

    @Test
    void register_NullDefaultModelId_ThrowsException() {
        InvalidChatConversationAiSettingsException ex = assertThrows(InvalidChatConversationAiSettingsException.class,
                () -> ChatConversationAiSettings.register(new ChatConversationId(conversationId), true, null));
        assertEquals("defaultModelId must not be null", ex.getMessage());
    }

    @Test
    void restore_DoesNotValidateAndDoesNotRecordEvents() {
        ChatConversationAiSettingsId id = ChatConversationAiSettingsId.generate();
        Instant createdAt = Instant.now().minusSeconds(100);
        Instant updatedAt = Instant.now().minusSeconds(50);

        ChatConversationAiSettings settings = ChatConversationAiSettings.restore(
                id,
                new ChatConversationId(conversationId),
                true,
                new AiModelId(modelId),
                createdAt,
                updatedAt);

        assertEquals(id, settings.id());
        assertEquals(new ChatConversationId(conversationId), settings.conversationId());
        assertEquals(true, settings.aiEnabled());
        assertEquals(new AiModelId(modelId), settings.defaultModelId());
        assertEquals(createdAt, settings.createdAt());
        assertEquals(updatedAt, settings.updatedAt());
        assertTrue(settings.domainEvents().isEmpty());
    }

    @Test
    void update_ValidData_UpdatesFieldsAndRecordsEvent() {
        ChatConversationAiSettings settings = ChatConversationAiSettings.register(
                new ChatConversationId(conversationId),
                true,
                new AiModelId(modelId));
        settings.clearDomainEvents();

        UUID newModelId = UUID.randomUUID();
        settings.update(false, new AiModelId(newModelId));

        assertEquals(false, settings.aiEnabled());
        assertEquals(new AiModelId(newModelId), settings.defaultModelId());

        List<com.mindconnect.domain.common.event.DomainEvent> events = settings.domainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof ChatConversationAiSettingsUpdatedEvent);
        ChatConversationAiSettingsUpdatedEvent event = (ChatConversationAiSettingsUpdatedEvent) events.get(0);
        assertEquals(settings.id(), event.chatConversationAiSettingsId());
        assertEquals(false, event.aiEnabled());
    }

    @Test
    void update_NullDefaultModelId_ThrowsException() {
        ChatConversationAiSettings settings = ChatConversationAiSettings.register(
                new ChatConversationId(conversationId),
                true,
                new AiModelId(modelId));
        settings.clearDomainEvents();

        InvalidChatConversationAiSettingsException ex = assertThrows(InvalidChatConversationAiSettingsException.class,
                () -> settings.update(true, null));
        assertEquals("defaultModelId must not be null", ex.getMessage());
    }

    @Test
    void delete_RecordsDeletedEvent() {
        ChatConversationAiSettings settings = ChatConversationAiSettings.register(
                new ChatConversationId(conversationId),
                true,
                new AiModelId(modelId));
        settings.clearDomainEvents();

        settings.delete();

        List<com.mindconnect.domain.common.event.DomainEvent> events = settings.domainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof ChatConversationAiSettingsDeletedEvent);
        ChatConversationAiSettingsDeletedEvent event = (ChatConversationAiSettingsDeletedEvent) events.get(0);
        assertEquals(settings.id(), event.chatConversationAiSettingsId());
    }

    @Test
    void register_WithFakeRepository_SavesAndFinds() {
        FakeChatConversationAiSettingsRepository repo = new FakeChatConversationAiSettingsRepository();

        ChatConversationAiSettings settings = ChatConversationAiSettings.register(
                new ChatConversationId(conversationId),
                true,
                new AiModelId(modelId));
        repo.save(settings);

        var found = repo.findById(settings.id());
        assertTrue(found.isPresent());
        assertEquals(new ChatConversationId(conversationId), found.get().conversationId());
    }

    @Test
    void existsByConversationId_ReturnsTrueWhenExists() {
        FakeChatConversationAiSettingsRepository repo = new FakeChatConversationAiSettingsRepository();

        ChatConversationAiSettings settings = ChatConversationAiSettings.register(
                new ChatConversationId(conversationId),
                true,
                new AiModelId(modelId));
        repo.save(settings);

        assertTrue(repo.existsByConversationId(new ChatConversationId(conversationId)));
    }

    @Test
    void existsByConversationId_ReturnsFalseWhenNotExists() {
        FakeChatConversationAiSettingsRepository repo = new FakeChatConversationAiSettingsRepository();

        assertTrue(!repo.existsByConversationId(new ChatConversationId(UUID.randomUUID())));
    }

    @Test
    void existsByConversationIdAndIdNot_ReturnsTrueWhenOtherHasConversationId() {
        FakeChatConversationAiSettingsRepository repo = new FakeChatConversationAiSettingsRepository();

        UUID conversationId1 = UUID.randomUUID();
        UUID conversationId2 = UUID.randomUUID();

        ChatConversationAiSettings settings1 = ChatConversationAiSettings.register(
                new ChatConversationId(conversationId1),
                true,
                new AiModelId(modelId));
        ChatConversationAiSettings settings2 = ChatConversationAiSettings.register(
                new ChatConversationId(conversationId2),
                true,
                new AiModelId(modelId));
        repo.save(settings1);
        repo.save(settings2);

        assertTrue(repo.existsByConversationIdAndIdNot(new ChatConversationId(conversationId1), settings2.id()));
    }

    @Test
    void existsByConversationIdAndIdNot_ReturnsFalseWhenSameId() {
        FakeChatConversationAiSettingsRepository repo = new FakeChatConversationAiSettingsRepository();

        ChatConversationAiSettings settings = ChatConversationAiSettings.register(
                new ChatConversationId(conversationId),
                true,
                new AiModelId(modelId));
        repo.save(settings);

        assertTrue(!repo.existsByConversationIdAndIdNot(new ChatConversationId(conversationId), settings.id()));
    }

    @Test
    void delete_RemovesFromRepository() {
        FakeChatConversationAiSettingsRepository repo = new FakeChatConversationAiSettingsRepository();

        ChatConversationAiSettings settings = ChatConversationAiSettings.register(
                new ChatConversationId(conversationId),
                true,
                new AiModelId(modelId));
        repo.save(settings);

        settings.delete();
        repo.delete(settings);

        assertTrue(repo.findById(settings.id()).isEmpty());
    }
}