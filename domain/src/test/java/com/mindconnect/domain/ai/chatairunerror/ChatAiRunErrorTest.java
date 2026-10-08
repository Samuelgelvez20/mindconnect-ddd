package com.mindconnect.domain.ai.chatairunerror;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mindconnect.domain.ai.chatairunerror.event.ChatAiRunErrorDeletedEvent;
import com.mindconnect.domain.ai.chatairunerror.event.ChatAiRunErrorRegisteredEvent;
import com.mindconnect.domain.ai.chatairunerror.event.ChatAiRunErrorUpdatedEvent;
import com.mindconnect.domain.ai.chatairunerror.exception.InvalidChatAiRunErrorException;
import com.mindconnect.domain.ai.chatairunerror.model.aggregate.ChatAiRunError;
import com.mindconnect.domain.ai.chatairunerror.model.valueobject.ChatAiRunErrorId;
import com.mindconnect.domain.ai.chatairun.model.valueobject.ChatAiRunId;

class ChatAiRunErrorTest {

    private ChatAiRunId aiRunId() {
        return new ChatAiRunId(UUID.randomUUID());
    }

    @Test
    void register_ValidData_CreatesErrorAndRecordsEvent() {
        ChatAiRunId aiRunId = aiRunId();

        ChatAiRunError error = ChatAiRunError.register(aiRunId, "Something went wrong", "ERR_001", "prov_123");

        assertNotNull(error.id());
        assertEquals(aiRunId, error.aiRunId());
        assertEquals("Something went wrong", error.errorMessage());
        assertEquals("ERR_001", error.errorCode());
        assertEquals("prov_123", error.providerErrorId());
        assertNotNull(error.createdAt());

        List<com.mindconnect.domain.common.event.DomainEvent> events = error.domainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof ChatAiRunErrorRegisteredEvent);
        ChatAiRunErrorRegisteredEvent event = (ChatAiRunErrorRegisteredEvent) events.get(0);
        assertEquals(error.id(), event.chatAiRunErrorId());
    }

    @Test
    void register_TrimsErrorMessage() {
        ChatAiRunError error = ChatAiRunError.register(aiRunId(), "  Something went wrong  ", "ERR_001", "prov_123");
        assertEquals("Something went wrong", error.errorMessage());
    }

    @Test
    void register_NullAiRunId_ThrowsException() {
        InvalidChatAiRunErrorException ex = assertThrows(InvalidChatAiRunErrorException.class,
                () -> ChatAiRunError.register(null, "Something went wrong", "ERR_001", "prov_123"));
        assertEquals("aiRunId must not be null", ex.getMessage());
    }

    @Test
    void register_NullErrorMessage_ThrowsException() {
        InvalidChatAiRunErrorException ex = assertThrows(InvalidChatAiRunErrorException.class,
                () -> ChatAiRunError.register(aiRunId(), null, "ERR_001", "prov_123"));
        assertEquals("errorMessage must not be blank", ex.getMessage());
    }

    @Test
    void register_BlankErrorMessage_ThrowsException() {
        InvalidChatAiRunErrorException ex = assertThrows(InvalidChatAiRunErrorException.class,
                () -> ChatAiRunError.register(aiRunId(), "   ", "ERR_001", "prov_123"));
        assertEquals("errorMessage must not be blank", ex.getMessage());
    }

    @Test
    void register_ErrorCodeExceedsMaxLength_ThrowsException() {
        String longCode = "a".repeat(ChatAiRunError.ERROR_CODE_MAX_LENGTH + 1);
        InvalidChatAiRunErrorException ex = assertThrows(InvalidChatAiRunErrorException.class,
                () -> ChatAiRunError.register(aiRunId(), "Something went wrong", longCode, "prov_123"));
        assertEquals("errorCode must have at most " + ChatAiRunError.ERROR_CODE_MAX_LENGTH + " characters", ex.getMessage());
    }

    @Test
    void register_ProviderErrorIdExceedsMaxLength_ThrowsException() {
        String longId = "a".repeat(ChatAiRunError.PROVIDER_ERROR_ID_MAX_LENGTH + 1);
        InvalidChatAiRunErrorException ex = assertThrows(InvalidChatAiRunErrorException.class,
                () -> ChatAiRunError.register(aiRunId(), "Something went wrong", "ERR_001", longId));
        assertEquals("providerErrorId must have at most " + ChatAiRunError.PROVIDER_ERROR_ID_MAX_LENGTH + " characters", ex.getMessage());
    }

    @Test
    void register_OptionalFieldsCanBeNull() {
        ChatAiRunError error = ChatAiRunError.register(aiRunId(), "Something went wrong", null, null);
        assertEquals("Something went wrong", error.errorMessage());
        assertNull(error.errorCode());
        assertNull(error.providerErrorId());
    }

    @Test
    void register_OptionalFieldsCanBeBlank() {
        ChatAiRunError error = ChatAiRunError.register(aiRunId(), "Something went wrong", "   ", "   ");
        assertEquals("Something went wrong", error.errorMessage());
        assertNull(error.errorCode());
        assertNull(error.providerErrorId());
    }

    @Test
    void restore_DoesNotValidateAndDoesNotRecordEvents() {
        ChatAiRunErrorId id = ChatAiRunErrorId.generate();
        ChatAiRunId aiRunId = aiRunId();
        Instant createdAt = Instant.now().minusSeconds(100);

        ChatAiRunError error = ChatAiRunError.restore(id, aiRunId, "Something went wrong", "ERR_001", "prov_123", createdAt);

        assertEquals(id, error.id());
        assertEquals(aiRunId, error.aiRunId());
        assertEquals("Something went wrong", error.errorMessage());
        assertEquals("ERR_001", error.errorCode());
        assertEquals("prov_123", error.providerErrorId());
        assertEquals(createdAt, error.createdAt());
        assertTrue(error.domainEvents().isEmpty());
    }

    @Test
    void update_ValidData_UpdatesFieldsAndRecordsEvent() {
        ChatAiRunError error = ChatAiRunError.register(aiRunId(), "Something went wrong", "ERR_001", "prov_123");
        error.clearDomainEvents();

        ChatAiRunId newAiRunId = new ChatAiRunId(UUID.randomUUID());

        error.update(newAiRunId, "Another error", "ERR_002", "prov_456");

        assertEquals(newAiRunId, error.aiRunId());
        assertEquals("Another error", error.errorMessage());
        assertEquals("ERR_002", error.errorCode());
        assertEquals("prov_456", error.providerErrorId());

        List<com.mindconnect.domain.common.event.DomainEvent> events = error.domainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof ChatAiRunErrorUpdatedEvent);
        ChatAiRunErrorUpdatedEvent event = (ChatAiRunErrorUpdatedEvent) events.get(0);
        assertEquals(error.id(), event.chatAiRunErrorId());
        assertEquals(newAiRunId, event.aiRunId());
        assertEquals("Another error", event.errorMessage());
        assertEquals("ERR_002", event.errorCode());
        assertEquals("prov_456", event.providerErrorId());
    }

    @Test
    void update_NullAiRunId_ThrowsException() {
        ChatAiRunError error = ChatAiRunError.register(aiRunId(), "Something went wrong", "ERR_001", "prov_123");
        error.clearDomainEvents();

        InvalidChatAiRunErrorException ex = assertThrows(InvalidChatAiRunErrorException.class,
                () -> error.update(null, "Another error", "ERR_002", "prov_456"));
        assertEquals("aiRunId must not be null", ex.getMessage());
    }

    @Test
    void update_NullErrorMessage_ThrowsException() {
        ChatAiRunError error = ChatAiRunError.register(aiRunId(), "Something went wrong", "ERR_001", "prov_123");
        error.clearDomainEvents();

        InvalidChatAiRunErrorException ex = assertThrows(InvalidChatAiRunErrorException.class,
                () -> error.update(aiRunId(), null, "ERR_002", "prov_456"));
        assertEquals("errorMessage must not be blank", ex.getMessage());
    }

    @Test
    void update_ErrorCodeExceedsMaxLength_ThrowsException() {
        ChatAiRunError error = ChatAiRunError.register(aiRunId(), "Something went wrong", "ERR_001", "prov_123");
        error.clearDomainEvents();

        String longCode = "a".repeat(ChatAiRunError.ERROR_CODE_MAX_LENGTH + 1);
        InvalidChatAiRunErrorException ex = assertThrows(InvalidChatAiRunErrorException.class,
                () -> error.update(aiRunId(), "Another error", longCode, "prov_456"));
        assertEquals("errorCode must have at most " + ChatAiRunError.ERROR_CODE_MAX_LENGTH + " characters", ex.getMessage());
    }

    @Test
    void update_ProviderErrorIdExceedsMaxLength_ThrowsException() {
        ChatAiRunError error = ChatAiRunError.register(aiRunId(), "Something went wrong", "ERR_001", "prov_123");
        error.clearDomainEvents();

        String longId = "a".repeat(ChatAiRunError.PROVIDER_ERROR_ID_MAX_LENGTH + 1);
        InvalidChatAiRunErrorException ex = assertThrows(InvalidChatAiRunErrorException.class,
                () -> error.update(aiRunId(), "Another error", "ERR_002", longId));
        assertEquals("providerErrorId must have at most " + ChatAiRunError.PROVIDER_ERROR_ID_MAX_LENGTH + " characters", ex.getMessage());
    }

    @Test
    void update_OptionalFieldsCanBeNull() {
        ChatAiRunError error = ChatAiRunError.register(aiRunId(), "Something went wrong", "ERR_001", "prov_123");
        error.clearDomainEvents();

        ChatAiRunId aiRunId = aiRunId();
        error.update(aiRunId, "Another error", null, null);
        assertEquals("Another error", error.errorMessage());
        assertNull(error.errorCode());
        assertNull(error.providerErrorId());
    }

    @Test
    void delete_RecordsDeletedEvent() {
        ChatAiRunError error = ChatAiRunError.register(aiRunId(), "Something went wrong", "ERR_001", "prov_123");
        error.clearDomainEvents();

        error.delete();

        List<com.mindconnect.domain.common.event.DomainEvent> events = error.domainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof ChatAiRunErrorDeletedEvent);
        ChatAiRunErrorDeletedEvent event = (ChatAiRunErrorDeletedEvent) events.get(0);
        assertEquals(error.id(), event.chatAiRunErrorId());
    }

    @Test
    void register_WithFakeRepository_SavesAndFinds() {
        FakeChatAiRunErrorRepository repo = new FakeChatAiRunErrorRepository();

        ChatAiRunId aiRunId = aiRunId();
        ChatAiRunError error = ChatAiRunError.register(aiRunId, "Something went wrong", "ERR_001", "prov_123");
        repo.save(error);

        var found = repo.findById(error.id());
        assertTrue(found.isPresent());
        assertEquals(aiRunId, found.get().aiRunId());
    }

    @Test
    void delete_RemovesFromRepository() {
        FakeChatAiRunErrorRepository repo = new FakeChatAiRunErrorRepository();

        ChatAiRunError error = ChatAiRunError.register(aiRunId(), "Something went wrong", "ERR_001", "prov_123");
        repo.save(error);

        error.delete();
        repo.delete(error);

        assertTrue(repo.findById(error.id()).isEmpty());
    }
}