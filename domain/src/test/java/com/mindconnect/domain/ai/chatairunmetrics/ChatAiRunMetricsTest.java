package com.mindconnect.domain.ai.chatairunmetrics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mindconnect.domain.ai.chatairunmetrics.event.ChatAiRunMetricsDeletedEvent;
import com.mindconnect.domain.ai.chatairunmetrics.event.ChatAiRunMetricsRegisteredEvent;
import com.mindconnect.domain.ai.chatairunmetrics.event.ChatAiRunMetricsUpdatedEvent;
import com.mindconnect.domain.ai.chatairunmetrics.exception.InvalidChatAiRunMetricsException;
import com.mindconnect.domain.ai.chatairunmetrics.model.aggregate.ChatAiRunMetrics;
import com.mindconnect.domain.ai.chatairunmetrics.model.valueobject.ChatAiRunMetricsId;
import com.mindconnect.domain.ai.chatairun.model.valueobject.ChatAiRunId;

class ChatAiRunMetricsTest {

    private ChatAiRunId aiRunId() {
        return new ChatAiRunId(UUID.randomUUID());
    }

    @Test
    void register_ValidData_CreatesMetricsAndRecordsEvent() {
        ChatAiRunId aiRunId = aiRunId();
        ChatAiRunMetrics metrics = ChatAiRunMetrics.register(aiRunId, 100, 50, 150, new BigDecimal("0.001234"));

        assertNotNull(metrics.id());
        assertEquals(aiRunId, metrics.aiRunId());
        assertEquals(100, metrics.promptTokens());
        assertEquals(50, metrics.completionTokens());
        assertEquals(150, metrics.totalTokens());
        assertEquals(new BigDecimal("0.001234"), metrics.cost());
        assertNotNull(metrics.createdAt());

        List<com.mindconnect.domain.common.event.DomainEvent> events = metrics.domainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof ChatAiRunMetricsRegisteredEvent);
        ChatAiRunMetricsRegisteredEvent event = (ChatAiRunMetricsRegisteredEvent) events.get(0);
        assertEquals(metrics.id(), event.chatAiRunMetricsId());
    }

    @Test
    void register_NullAiRunId_ThrowsException() {
        InvalidChatAiRunMetricsException ex = assertThrows(InvalidChatAiRunMetricsException.class,
                () -> ChatAiRunMetrics.register(null, 100, 50, 150, new BigDecimal("0.001234")));
        assertEquals("aiRunId must not be null", ex.getMessage());
    }

    @Test
    void register_NullCost_ThrowsException() {
        InvalidChatAiRunMetricsException ex = assertThrows(InvalidChatAiRunMetricsException.class,
                () -> ChatAiRunMetrics.register(aiRunId(), 100, 50, 150, null));
        assertEquals("cost must not be null", ex.getMessage());
    }

    @Test
    void restore_DoesNotValidateAndDoesNotRecordEvents() {
        ChatAiRunMetricsId id = ChatAiRunMetricsId.generate();
        ChatAiRunId aiRunId = aiRunId();
        Instant createdAt = Instant.now().minusSeconds(100);

        ChatAiRunMetrics metrics = ChatAiRunMetrics.restore(id, aiRunId, 100, 50, 150, new BigDecimal("0.001234"), createdAt);

        assertEquals(id, metrics.id());
        assertEquals(aiRunId, metrics.aiRunId());
        assertEquals(100, metrics.promptTokens());
        assertEquals(50, metrics.completionTokens());
        assertEquals(150, metrics.totalTokens());
        assertEquals(new BigDecimal("0.001234"), metrics.cost());
        assertEquals(createdAt, metrics.createdAt());
        assertTrue(metrics.domainEvents().isEmpty());
    }

    @Test
    void update_ValidData_UpdatesFieldsAndRecordsEvent() {
        ChatAiRunId aiRunId = aiRunId();
        ChatAiRunMetrics metrics = ChatAiRunMetrics.register(aiRunId, 100, 50, 150, new BigDecimal("0.001234"));
        metrics.clearDomainEvents();

        ChatAiRunId newAiRunId = new ChatAiRunId(UUID.randomUUID());

        metrics.update(newAiRunId, 200, 75, 275, new BigDecimal("0.002468"));

        assertEquals(newAiRunId, metrics.aiRunId());
        assertEquals(200, metrics.promptTokens());
        assertEquals(75, metrics.completionTokens());
        assertEquals(275, metrics.totalTokens());
        assertEquals(new BigDecimal("0.002468"), metrics.cost());

        List<com.mindconnect.domain.common.event.DomainEvent> events = metrics.domainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof ChatAiRunMetricsUpdatedEvent);
        ChatAiRunMetricsUpdatedEvent event = (ChatAiRunMetricsUpdatedEvent) events.get(0);
        assertEquals(metrics.id(), event.chatAiRunMetricsId());
        assertEquals(newAiRunId, event.aiRunId());
        assertEquals(200, event.promptTokens());
        assertEquals(75, event.completionTokens());
        assertEquals(275, event.totalTokens());
        assertEquals(new BigDecimal("0.002468"), event.cost());
    }

    @Test
    void update_NullAiRunId_ThrowsException() {
        ChatAiRunMetrics metrics = ChatAiRunMetrics.register(aiRunId(), 100, 50, 150, new BigDecimal("0.001234"));
        metrics.clearDomainEvents();

        InvalidChatAiRunMetricsException ex = assertThrows(InvalidChatAiRunMetricsException.class,
                () -> metrics.update(null, 200, 75, 275, new BigDecimal("0.002468")));
        assertEquals("aiRunId must not be null", ex.getMessage());
    }

    @Test
    void update_NullCost_ThrowsException() {
        ChatAiRunMetrics metrics = ChatAiRunMetrics.register(aiRunId(), 100, 50, 150, new BigDecimal("0.001234"));
        metrics.clearDomainEvents();

        InvalidChatAiRunMetricsException ex = assertThrows(InvalidChatAiRunMetricsException.class,
                () -> metrics.update(aiRunId(), 200, 75, 275, null));
        assertEquals("cost must not be null", ex.getMessage());
    }

    @Test
    void delete_RecordsDeletedEvent() {
        ChatAiRunMetrics metrics = ChatAiRunMetrics.register(aiRunId(), 100, 50, 150, new BigDecimal("0.001234"));
        metrics.clearDomainEvents();

        metrics.delete();

        List<com.mindconnect.domain.common.event.DomainEvent> events = metrics.domainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof ChatAiRunMetricsDeletedEvent);
        ChatAiRunMetricsDeletedEvent event = (ChatAiRunMetricsDeletedEvent) events.get(0);
        assertEquals(metrics.id(), event.chatAiRunMetricsId());
    }

    @Test
    void register_WithFakeRepository_SavesAndFinds() {
        FakeChatAiRunMetricsRepository repo = new FakeChatAiRunMetricsRepository();

        ChatAiRunId aiRunId = aiRunId();
        ChatAiRunMetrics metrics = ChatAiRunMetrics.register(aiRunId, 100, 50, 150, new BigDecimal("0.001234"));
        repo.save(metrics);

        var found = repo.findById(metrics.id());
        assertTrue(found.isPresent());
        assertEquals(aiRunId, found.get().aiRunId());
    }

    @Test
    void existsByAiRunId_ReturnsTrueWhenExists() {
        FakeChatAiRunMetricsRepository repo = new FakeChatAiRunMetricsRepository();

        ChatAiRunId aiRunId = aiRunId();
        ChatAiRunMetrics metrics = ChatAiRunMetrics.register(aiRunId, 100, 50, 150, new BigDecimal("0.001234"));
        repo.save(metrics);

        assertTrue(repo.existsByAiRunId(aiRunId));
    }

    @Test
    void existsByAiRunId_ReturnsFalseWhenNotExists() {
        FakeChatAiRunMetricsRepository repo = new FakeChatAiRunMetricsRepository();

        assertTrue(!repo.existsByAiRunId(new ChatAiRunId(UUID.randomUUID())));
    }

    @Test
    void existsByAiRunIdAndIdNot_ReturnsTrueWhenOtherHasAiRunId() {
        FakeChatAiRunMetricsRepository repo = new FakeChatAiRunMetricsRepository();

        ChatAiRunId aiRunId1 = new ChatAiRunId(UUID.randomUUID());
        ChatAiRunId aiRunId2 = new ChatAiRunId(UUID.randomUUID());
        ChatAiRunMetrics metrics1 = ChatAiRunMetrics.register(aiRunId1, 100, 50, 150, new BigDecimal("0.001234"));
        ChatAiRunMetrics metrics2 = ChatAiRunMetrics.register(aiRunId2, 200, 75, 275, new BigDecimal("0.002468"));
        repo.save(metrics1);
        repo.save(metrics2);

        assertTrue(repo.existsByAiRunIdAndIdNot(aiRunId1, metrics2.id()));
    }

    @Test
    void existsByAiRunIdAndIdNot_ReturnsFalseWhenSameId() {
        FakeChatAiRunMetricsRepository repo = new FakeChatAiRunMetricsRepository();

        ChatAiRunMetrics metrics = ChatAiRunMetrics.register(aiRunId(), 100, 50, 150, new BigDecimal("0.001234"));
        repo.save(metrics);

        assertTrue(!repo.existsByAiRunIdAndIdNot(aiRunId(), metrics.id()));
    }

    @Test
    void delete_RemovesFromRepository() {
        FakeChatAiRunMetricsRepository repo = new FakeChatAiRunMetricsRepository();

        ChatAiRunMetrics metrics = ChatAiRunMetrics.register(aiRunId(), 100, 50, 150, new BigDecimal("0.001234"));
        repo.save(metrics);

        metrics.delete();
        repo.delete(metrics);

        assertTrue(repo.findById(metrics.id()).isEmpty());
    }
}