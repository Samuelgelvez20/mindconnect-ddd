package com.mindconnect.domain.ai.chatairun;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mindconnect.domain.ai.chatairun.event.ChatAiRunDeletedEvent;
import com.mindconnect.domain.ai.chatairun.event.ChatAiRunRegisteredEvent;
import com.mindconnect.domain.ai.chatairun.event.ChatAiRunUpdatedEvent;
import com.mindconnect.domain.ai.chatairun.exception.InvalidChatAiRunException;
import com.mindconnect.domain.ai.chatairun.model.aggregate.ChatAiRun;
import com.mindconnect.domain.ai.chatairun.model.valueobject.ChatAiRunId;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.chatmessage.model.valueobject.ChatMessageId;
import com.mindconnect.domain.ai.aimodel.model.valueobject.AiModelId;
import com.mindconnect.domain.ai.chatairunstatus.model.valueobject.ChatAiRunStatusId;

class ChatAiRunTest {

    private ChatConversationId conversationId() {
        return new ChatConversationId(UUID.randomUUID());
    }

    private ChatMessageId messageId() {
        return new ChatMessageId(UUID.randomUUID());
    }

    private AiModelId modelId() {
        return new AiModelId(UUID.randomUUID());
    }

    private ChatAiRunStatusId aiRunStatusId() {
        return new ChatAiRunStatusId(UUID.randomUUID());
    }

    @Test
    void register_ValidData_CreatesRunAndRecordsEvent() {
        ChatConversationId conversationId = conversationId();
        ChatMessageId messageId = messageId();
        AiModelId modelId = modelId();
        ChatAiRunStatusId aiRunStatusId = aiRunStatusId();

        ChatAiRun run = ChatAiRun.register(conversationId, messageId, modelId, aiRunStatusId);

        assertNotNull(run.id());
        assertEquals(conversationId, run.conversationId());
        assertEquals(messageId, run.messageId());
        assertEquals(modelId, run.modelId());
        assertEquals(aiRunStatusId, run.aiRunStatusId());
        assertNotNull(run.createdAt());
        assertNotNull(run.updatedAt());

        List<com.mindconnect.domain.common.event.DomainEvent> events = run.domainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof ChatAiRunRegisteredEvent);
        ChatAiRunRegisteredEvent event = (ChatAiRunRegisteredEvent) events.get(0);
        assertEquals(run.id(), event.chatAiRunId());
    }

    @Test
    void register_NullConversationId_ThrowsException() {
        InvalidChatAiRunException ex = assertThrows(InvalidChatAiRunException.class,
                () -> ChatAiRun.register(null, messageId(), modelId(), aiRunStatusId()));
        assertEquals("conversationId must not be null", ex.getMessage());
    }

    @Test
    void register_NullMessageId_ThrowsException() {
        InvalidChatAiRunException ex = assertThrows(InvalidChatAiRunException.class,
                () -> ChatAiRun.register(conversationId(), null, modelId(), aiRunStatusId()));
        assertEquals("messageId must not be null", ex.getMessage());
    }

    @Test
    void register_NullModelId_ThrowsException() {
        InvalidChatAiRunException ex = assertThrows(InvalidChatAiRunException.class,
                () -> ChatAiRun.register(conversationId(), messageId(), null, aiRunStatusId()));
        assertEquals("modelId must not be null", ex.getMessage());
    }

    @Test
    void register_NullAiRunStatusId_ThrowsException() {
        InvalidChatAiRunException ex = assertThrows(InvalidChatAiRunException.class,
                () -> ChatAiRun.register(conversationId(), messageId(), modelId(), null));
        assertEquals("aiRunStatusId must not be null", ex.getMessage());
    }

    @Test
    void restore_DoesNotValidateAndDoesNotRecordEvents() {
        ChatAiRunId id = ChatAiRunId.generate();
        ChatConversationId conversationId = conversationId();
        ChatMessageId messageId = messageId();
        AiModelId modelId = modelId();
        ChatAiRunStatusId aiRunStatusId = aiRunStatusId();
        Instant createdAt = Instant.now().minusSeconds(100);
        Instant updatedAt = Instant.now().minusSeconds(50);

        ChatAiRun run = ChatAiRun.restore(id, conversationId, messageId, modelId, aiRunStatusId, createdAt, updatedAt);

        assertEquals(id, run.id());
        assertEquals(conversationId, run.conversationId());
        assertEquals(messageId, run.messageId());
        assertEquals(modelId, run.modelId());
        assertEquals(aiRunStatusId, run.aiRunStatusId());
        assertEquals(createdAt, run.createdAt());
        assertEquals(updatedAt, run.updatedAt());
        assertTrue(run.domainEvents().isEmpty());
    }

    @Test
    void update_ValidData_UpdatesFieldsAndRecordsEvent() {
        ChatAiRun run = ChatAiRun.register(conversationId(), messageId(), modelId(), aiRunStatusId());
        run.clearDomainEvents();

        ChatConversationId newConversationId = new ChatConversationId(UUID.randomUUID());
        ChatMessageId newMessageId = new ChatMessageId(UUID.randomUUID());
        AiModelId newModelId = new AiModelId(UUID.randomUUID());
        ChatAiRunStatusId newAiRunStatusId = new ChatAiRunStatusId(UUID.randomUUID());

        run.update(newConversationId, newMessageId, newModelId, newAiRunStatusId);

        assertEquals(newConversationId, run.conversationId());
        assertEquals(newMessageId, run.messageId());
        assertEquals(newModelId, run.modelId());
        assertEquals(newAiRunStatusId, run.aiRunStatusId());

        List<com.mindconnect.domain.common.event.DomainEvent> events = run.domainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof ChatAiRunUpdatedEvent);
        ChatAiRunUpdatedEvent event = (ChatAiRunUpdatedEvent) events.get(0);
        assertEquals(run.id(), event.chatAiRunId());
        assertEquals(newAiRunStatusId, event.aiRunStatusId());
    }

    @Test
    void update_NullConversationId_ThrowsException() {
        ChatAiRun run = ChatAiRun.register(conversationId(), messageId(), modelId(), aiRunStatusId());
        run.clearDomainEvents();

        InvalidChatAiRunException ex = assertThrows(InvalidChatAiRunException.class,
                () -> run.update(null, messageId(), modelId(), aiRunStatusId()));
        assertEquals("conversationId must not be null", ex.getMessage());
    }

    @Test
    void update_NullMessageId_ThrowsException() {
        ChatAiRun run = ChatAiRun.register(conversationId(), messageId(), modelId(), aiRunStatusId());
        run.clearDomainEvents();

        InvalidChatAiRunException ex = assertThrows(InvalidChatAiRunException.class,
                () -> run.update(conversationId(), null, modelId(), aiRunStatusId()));
        assertEquals("messageId must not be null", ex.getMessage());
    }

    @Test
    void update_NullModelId_ThrowsException() {
        ChatAiRun run = ChatAiRun.register(conversationId(), messageId(), modelId(), aiRunStatusId());
        run.clearDomainEvents();

        InvalidChatAiRunException ex = assertThrows(InvalidChatAiRunException.class,
                () -> run.update(conversationId(), messageId(), null, aiRunStatusId()));
        assertEquals("modelId must not be null", ex.getMessage());
    }

    @Test
    void update_NullAiRunStatusId_ThrowsException() {
        ChatAiRun run = ChatAiRun.register(conversationId(), messageId(), modelId(), aiRunStatusId());
        run.clearDomainEvents();

        InvalidChatAiRunException ex = assertThrows(InvalidChatAiRunException.class,
                () -> run.update(conversationId(), messageId(), modelId(), null));
        assertEquals("aiRunStatusId must not be null", ex.getMessage());
    }

    @Test
    void delete_RecordsDeletedEvent() {
        ChatAiRun run = ChatAiRun.register(conversationId(), messageId(), modelId(), aiRunStatusId());
        run.clearDomainEvents();

        run.delete();

        List<com.mindconnect.domain.common.event.DomainEvent> events = run.domainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof ChatAiRunDeletedEvent);
        ChatAiRunDeletedEvent event = (ChatAiRunDeletedEvent) events.get(0);
        assertEquals(run.id(), event.chatAiRunId());
    }

    @Test
    void register_WithFakeRepository_SavesAndFinds() {
        FakeChatAiRunRepository repo = new FakeChatAiRunRepository();

        ChatConversationId conversationId = conversationId();
        ChatMessageId messageId = messageId();
        AiModelId modelId = modelId();
        ChatAiRunStatusId aiRunStatusId = aiRunStatusId();

        ChatAiRun run = ChatAiRun.register(conversationId, messageId, modelId, aiRunStatusId);
        repo.save(run);

        var found = repo.findById(run.id());
        assertTrue(found.isPresent());
        assertEquals(conversationId, found.get().conversationId());
    }

    @Test
    void delete_RemovesFromRepository() {
        FakeChatAiRunRepository repo = new FakeChatAiRunRepository();

        ChatAiRun run = ChatAiRun.register(conversationId(), messageId(), modelId(), aiRunStatusId());
        repo.save(run);

        run.delete();
        repo.delete(run);

        assertTrue(repo.findById(run.id()).isEmpty());
    }
}