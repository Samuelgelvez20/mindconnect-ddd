package com.mindconnect.domain.ai.chatairunstatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mindconnect.domain.ai.chatairunstatus.event.ChatAiRunStatusDeletedEvent;
import com.mindconnect.domain.ai.chatairunstatus.event.ChatAiRunStatusRegisteredEvent;
import com.mindconnect.domain.ai.chatairunstatus.event.ChatAiRunStatusUpdatedEvent;
import com.mindconnect.domain.ai.chatairunstatus.exception.InvalidChatAiRunStatusException;
import com.mindconnect.domain.ai.chatairunstatus.model.aggregate.ChatAiRunStatus;
import com.mindconnect.domain.ai.chatairunstatus.model.valueobject.ChatAiRunStatusId;

class ChatAiRunStatusTest {

    @Test
    void register_ValidData_CreatesStatusAndRecordsEvent() {
        ChatAiRunStatus status = ChatAiRunStatus.register("PENDING");

        assertNotNull(status.id());
        assertEquals("PENDING", status.name());
        assertNotNull(status.createdAt());
        assertNotNull(status.updatedAt());

        List<com.mindconnect.domain.common.event.DomainEvent> events = status.domainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof ChatAiRunStatusRegisteredEvent);
        ChatAiRunStatusRegisteredEvent event = (ChatAiRunStatusRegisteredEvent) events.get(0);
        assertEquals(status.id(), event.chatAiRunStatusId());
    }

    @Test
    void register_TrimsName() {
        ChatAiRunStatus status = ChatAiRunStatus.register("  PENDING  ");
        assertEquals("PENDING", status.name());
    }

    @Test
    void register_NullName_ThrowsException() {
        InvalidChatAiRunStatusException ex = assertThrows(InvalidChatAiRunStatusException.class,
                () -> ChatAiRunStatus.register(null));
        assertEquals("name must not be blank", ex.getMessage());
    }

    @Test
    void register_BlankName_ThrowsException() {
        InvalidChatAiRunStatusException ex = assertThrows(InvalidChatAiRunStatusException.class,
                () -> ChatAiRunStatus.register("   "));
        assertEquals("name must not be blank", ex.getMessage());
    }

    @Test
    void register_NameExceedsMaxLength_ThrowsException() {
        String longName = "a".repeat(ChatAiRunStatus.NAME_MAX_LENGTH + 1);
        InvalidChatAiRunStatusException ex = assertThrows(InvalidChatAiRunStatusException.class,
                () -> ChatAiRunStatus.register(longName));
        assertEquals("name must have at most " + ChatAiRunStatus.NAME_MAX_LENGTH + " characters", ex.getMessage());
    }

    @Test
    void restore_DoesNotValidateAndDoesNotRecordEvents() {
        ChatAiRunStatusId id = ChatAiRunStatusId.generate();
        Instant createdAt = Instant.now().minusSeconds(100);
        Instant updatedAt = Instant.now().minusSeconds(50);

        ChatAiRunStatus status = ChatAiRunStatus.restore(id, "PENDING", createdAt, updatedAt);

        assertEquals(id, status.id());
        assertEquals("PENDING", status.name());
        assertEquals(createdAt, status.createdAt());
        assertEquals(updatedAt, status.updatedAt());
        assertTrue(status.domainEvents().isEmpty());
    }

    @Test
    void update_ValidData_UpdatesFieldsAndRecordsEvent() {
        ChatAiRunStatus status = ChatAiRunStatus.register("PENDING");
        status.clearDomainEvents();

        status.update("COMPLETED");

        assertEquals("COMPLETED", status.name());

        List<com.mindconnect.domain.common.event.DomainEvent> events = status.domainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof ChatAiRunStatusUpdatedEvent);
        ChatAiRunStatusUpdatedEvent event = (ChatAiRunStatusUpdatedEvent) events.get(0);
        assertEquals(status.id(), event.chatAiRunStatusId());
        assertEquals("COMPLETED", event.name());
    }

    @Test
    void update_NullName_ThrowsException() {
        ChatAiRunStatus status = ChatAiRunStatus.register("PENDING");
        status.clearDomainEvents();

        InvalidChatAiRunStatusException ex = assertThrows(InvalidChatAiRunStatusException.class,
                () -> status.update(null));
        assertEquals("name must not be blank", ex.getMessage());
    }

    @Test
    void update_BlankName_ThrowsException() {
        ChatAiRunStatus status = ChatAiRunStatus.register("PENDING");
        status.clearDomainEvents();

        InvalidChatAiRunStatusException ex = assertThrows(InvalidChatAiRunStatusException.class,
                () -> status.update("   "));
        assertEquals("name must not be blank", ex.getMessage());
    }

    @Test
    void update_NameExceedsMaxLength_ThrowsException() {
        ChatAiRunStatus status = ChatAiRunStatus.register("PENDING");
        status.clearDomainEvents();

        String longName = "a".repeat(ChatAiRunStatus.NAME_MAX_LENGTH + 1);
        InvalidChatAiRunStatusException ex = assertThrows(InvalidChatAiRunStatusException.class,
                () -> status.update(longName));
        assertEquals("name must have at most " + ChatAiRunStatus.NAME_MAX_LENGTH + " characters", ex.getMessage());
    }

    @Test
    void delete_RecordsDeletedEvent() {
        ChatAiRunStatus status = ChatAiRunStatus.register("PENDING");
        status.clearDomainEvents();

        status.delete();

        List<com.mindconnect.domain.common.event.DomainEvent> events = status.domainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof ChatAiRunStatusDeletedEvent);
        ChatAiRunStatusDeletedEvent event = (ChatAiRunStatusDeletedEvent) events.get(0);
        assertEquals(status.id(), event.chatAiRunStatusId());
    }

    @Test
    void register_WithFakeRepository_SavesAndFinds() {
        FakeChatAiRunStatusRepository repo = new FakeChatAiRunStatusRepository();

        ChatAiRunStatus status = ChatAiRunStatus.register("PENDING");
        repo.save(status);

        var found = repo.findById(status.id());
        assertTrue(found.isPresent());
        assertEquals("PENDING", found.get().name());
    }

    @Test
    void existsByName_ReturnsTrueWhenExists() {
        FakeChatAiRunStatusRepository repo = new FakeChatAiRunStatusRepository();

        ChatAiRunStatus status = ChatAiRunStatus.register("PENDING");
        repo.save(status);

        assertTrue(repo.existsByName("PENDING"));
    }

    @Test
    void existsByName_ReturnsFalseWhenNotExists() {
        FakeChatAiRunStatusRepository repo = new FakeChatAiRunStatusRepository();

        assertTrue(!repo.existsByName("NON_EXISTENT"));
    }

    @Test
    void existsByNameAndIdNot_ReturnsTrueWhenOtherHasName() {
        FakeChatAiRunStatusRepository repo = new FakeChatAiRunStatusRepository();

        ChatAiRunStatus status1 = ChatAiRunStatus.register("PENDING");
        ChatAiRunStatus status2 = ChatAiRunStatus.register("COMPLETED");
        repo.save(status1);
        repo.save(status2);

        assertTrue(repo.existsByNameAndIdNot("PENDING", status2.id()));
    }

    @Test
    void existsByNameAndIdNot_ReturnsFalseWhenSameId() {
        FakeChatAiRunStatusRepository repo = new FakeChatAiRunStatusRepository();

        ChatAiRunStatus status = ChatAiRunStatus.register("PENDING");
        repo.save(status);

        assertTrue(!repo.existsByNameAndIdNot("PENDING", status.id()));
    }

    @Test
    void delete_RemovesFromRepository() {
        FakeChatAiRunStatusRepository repo = new FakeChatAiRunStatusRepository();

        ChatAiRunStatus status = ChatAiRunStatus.register("PENDING");
        repo.save(status);

        status.delete();
        repo.delete(status);

        assertTrue(repo.findById(status.id()).isEmpty());
    }
}