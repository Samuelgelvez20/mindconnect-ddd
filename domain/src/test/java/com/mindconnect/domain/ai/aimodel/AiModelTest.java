package com.mindconnect.domain.ai.aimodel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mindconnect.domain.ai.aimodel.event.AiModelDeletedEvent;
import com.mindconnect.domain.ai.aimodel.event.AiModelRegisteredEvent;
import com.mindconnect.domain.ai.aimodel.event.AiModelUpdatedEvent;
import com.mindconnect.domain.ai.aimodel.exception.InvalidAiModelException;
import com.mindconnect.domain.ai.aimodel.model.aggregate.AiModel;
import com.mindconnect.domain.ai.aimodel.model.valueobject.AiModelId;
import com.mindconnect.domain.ai.aiprovider.model.valueobject.AiProviderId;

class AiModelTest {

    private static final BigDecimal PRICE_1 = new BigDecimal("0.00100000");
    private static final BigDecimal PRICE_2 = new BigDecimal("0.00200000");
    private final UUID providerId = UUID.randomUUID();

    @Test
    void register_ValidData_CreatesModelAndRecordsEvent() {
        AiProviderId aiProviderId = new AiProviderId(providerId);
        String name = "GPT-4";
        String modelKey = "gpt-4";
        BigDecimal inputTokenPrice = PRICE_1;
        BigDecimal outputTokenPrice = PRICE_2;
        int maxTokens = 8192;
        int contextWindow = 128000;

        AiModel model = AiModel.register(aiProviderId, name, modelKey, inputTokenPrice, outputTokenPrice, maxTokens, contextWindow);

        assertNotNull(model.id());
        assertEquals(aiProviderId, model.aiProviderId());
        assertEquals("GPT-4", model.name());
        assertEquals("gpt-4", model.modelKey());
        assertEquals(PRICE_1, model.inputTokenPrice());
        assertEquals(PRICE_2, model.outputTokenPrice());
        assertEquals(8192, model.maxTokens());
        assertEquals(128000, model.contextWindow());
        assertTrue(model.active());
        assertNotNull(model.createdAt());
        assertNotNull(model.updatedAt());

        List<com.mindconnect.domain.common.event.DomainEvent> events = model.domainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof AiModelRegisteredEvent);
        AiModelRegisteredEvent event = (AiModelRegisteredEvent) events.get(0);
        assertEquals(model.id(), event.aiModelId());
    }

    @Test
    void register_TrimsName() {
        AiModel model = AiModel.register(new AiProviderId(providerId), "  GPT-4  ", "gpt-4", PRICE_1, PRICE_2, 8192, 128000);
        assertEquals("GPT-4", model.name());
    }

    @Test
    void register_TrimsModelKey() {
        AiModel model = AiModel.register(new AiProviderId(providerId), "GPT-4", "  gpt-4  ", PRICE_1, PRICE_2, 8192, 128000);
        assertEquals("gpt-4", model.modelKey());
    }

    @Test
    void register_NullProviderId_ThrowsException() {
        InvalidAiModelException ex = assertThrows(InvalidAiModelException.class,
                () -> AiModel.register(null, "GPT-4", "gpt-4", PRICE_1, PRICE_2, 8192, 128000));
        assertEquals("aiProviderId must not be null", ex.getMessage());
    }

    @Test
    void register_NullName_ThrowsException() {
        InvalidAiModelException ex = assertThrows(InvalidAiModelException.class,
                () -> AiModel.register(new AiProviderId(providerId), null, "gpt-4", PRICE_1, PRICE_2, 8192, 128000));
        assertEquals("name must not be blank", ex.getMessage());
    }

    @Test
    void register_BlankName_ThrowsException() {
        InvalidAiModelException ex = assertThrows(InvalidAiModelException.class,
                () -> AiModel.register(new AiProviderId(providerId), "   ", "gpt-4", PRICE_1, PRICE_2, 8192, 128000));
        assertEquals("name must not be blank", ex.getMessage());
    }

    @Test
    void register_NameExceedsMaxLength_ThrowsException() {
        String longName = "a".repeat(AiModel.NAME_MAX_LENGTH + 1);
        InvalidAiModelException ex = assertThrows(InvalidAiModelException.class,
                () -> AiModel.register(new AiProviderId(providerId), longName, "gpt-4", PRICE_1, PRICE_2, 8192, 128000));
        assertEquals("name must have at most " + AiModel.NAME_MAX_LENGTH + " characters", ex.getMessage());
    }

    @Test
    void register_NullModelKey_ThrowsException() {
        InvalidAiModelException ex = assertThrows(InvalidAiModelException.class,
                () -> AiModel.register(new AiProviderId(providerId), "GPT-4", null, PRICE_1, PRICE_2, 8192, 128000));
        assertEquals("modelKey must not be blank", ex.getMessage());
    }

    @Test
    void register_BlankModelKey_ThrowsException() {
        InvalidAiModelException ex = assertThrows(InvalidAiModelException.class,
                () -> AiModel.register(new AiProviderId(providerId), "GPT-4", "   ", PRICE_1, PRICE_2, 8192, 128000));
        assertEquals("modelKey must not be blank", ex.getMessage());
    }

    @Test
    void register_ModelKeyExceedsMaxLength_ThrowsException() {
        String longKey = "a".repeat(AiModel.MODEL_KEY_MAX_LENGTH + 1);
        InvalidAiModelException ex = assertThrows(InvalidAiModelException.class,
                () -> AiModel.register(new AiProviderId(providerId), "GPT-4", longKey, PRICE_1, PRICE_2, 8192, 128000));
        assertEquals("modelKey must have at most " + AiModel.MODEL_KEY_MAX_LENGTH + " characters", ex.getMessage());
    }

    @Test
    void register_NullInputTokenPrice_ThrowsException() {
        InvalidAiModelException ex = assertThrows(InvalidAiModelException.class,
                () -> AiModel.register(new AiProviderId(providerId), "GPT-4", "gpt-4", null, PRICE_2, 8192, 128000));
        assertEquals("inputTokenPrice must not be null", ex.getMessage());
    }

    @Test
    void register_NullOutputTokenPrice_ThrowsException() {
        InvalidAiModelException ex = assertThrows(InvalidAiModelException.class,
                () -> AiModel.register(new AiProviderId(providerId), "GPT-4", "gpt-4", PRICE_1, null, 8192, 128000));
        assertEquals("outputTokenPrice must not be null", ex.getMessage());
    }

    @Test
    void restore_DoesNotValidateAndDoesNotRecordEvents() {
        AiModelId id = AiModelId.generate();
        Instant createdAt = Instant.now().minusSeconds(100);
        Instant updatedAt = Instant.now().minusSeconds(50);
        AiProviderId aiProviderId = new AiProviderId(providerId);

        AiModel model = AiModel.restore(id, aiProviderId, "GPT-4", "gpt-4", PRICE_1, PRICE_2, 8192, 128000, true, createdAt, updatedAt);

        assertEquals(id, model.id());
        assertEquals(aiProviderId, model.aiProviderId());
        assertEquals("GPT-4", model.name());
        assertEquals("gpt-4", model.modelKey());
        assertEquals(PRICE_1, model.inputTokenPrice());
        assertEquals(PRICE_2, model.outputTokenPrice());
        assertEquals(8192, model.maxTokens());
        assertEquals(128000, model.contextWindow());
        assertTrue(model.active());
        assertEquals(createdAt, model.createdAt());
        assertEquals(updatedAt, model.updatedAt());
        assertTrue(model.domainEvents().isEmpty());
    }

    @Test
    void update_ValidData_UpdatesFieldsAndRecordsEvent() {
        AiModel model = AiModel.register(new AiProviderId(providerId), "GPT-4", "gpt-4", PRICE_1, PRICE_2, 8192, 128000);
        model.clearDomainEvents();

        BigDecimal newInputPrice = new BigDecimal("0.00150000");
        BigDecimal newOutputPrice = new BigDecimal("0.00300000");

        model.update(new AiProviderId(providerId), "GPT-4 Turbo", "gpt-4-turbo", newInputPrice, newOutputPrice, 4096, 128000, false);

        assertEquals(new AiProviderId(providerId), model.aiProviderId());
        assertEquals("GPT-4 Turbo", model.name());
        assertEquals("gpt-4-turbo", model.modelKey());
        assertEquals(newInputPrice, model.inputTokenPrice());
        assertEquals(newOutputPrice, model.outputTokenPrice());
        assertEquals(4096, model.maxTokens());
        assertEquals(128000, model.contextWindow());
        assertEquals(false, model.active());

        List<com.mindconnect.domain.common.event.DomainEvent> events = model.domainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof AiModelUpdatedEvent);
        AiModelUpdatedEvent event = (AiModelUpdatedEvent) events.get(0);
        assertEquals(model.id(), event.aiModelId());
        assertEquals(new AiProviderId(providerId), event.aiProviderId());
        assertEquals("GPT-4 Turbo", event.name());
        assertEquals("gpt-4-turbo", event.modelKey());
        assertEquals(newInputPrice, event.inputTokenPrice());
        assertEquals(newOutputPrice, event.outputTokenPrice());
        assertEquals(4096, event.maxTokens());
        assertEquals(128000, event.contextWindow());
        assertEquals(false, event.active());
    }

    @Test
    void update_NullProviderId_ThrowsException() {
        AiModel model = AiModel.register(new AiProviderId(providerId), "GPT-4", "gpt-4", PRICE_1, PRICE_2, 8192, 128000);
        model.clearDomainEvents();

        InvalidAiModelException ex = assertThrows(InvalidAiModelException.class,
                () -> model.update(null, "GPT-4", "gpt-4", PRICE_1, PRICE_2, 8192, 128000, true));
        assertEquals("aiProviderId must not be null", ex.getMessage());
    }

    @Test
    void update_NullName_ThrowsException() {
        AiModel model = AiModel.register(new AiProviderId(providerId), "GPT-4", "gpt-4", PRICE_1, PRICE_2, 8192, 128000);
        model.clearDomainEvents();

        InvalidAiModelException ex = assertThrows(InvalidAiModelException.class,
                () -> model.update(new AiProviderId(providerId), null, "gpt-4", PRICE_1, PRICE_2, 8192, 128000, true));
        assertEquals("name must not be blank", ex.getMessage());
    }

    @Test
    void update_NameExceedsMaxLength_ThrowsException() {
        AiModel model = AiModel.register(new AiProviderId(providerId), "GPT-4", "gpt-4", PRICE_1, PRICE_2, 8192, 128000);
        model.clearDomainEvents();

        String longName = "a".repeat(AiModel.NAME_MAX_LENGTH + 1);
        InvalidAiModelException ex = assertThrows(InvalidAiModelException.class,
                () -> model.update(new AiProviderId(providerId), longName, "gpt-4", PRICE_1, PRICE_2, 8192, 128000, true));
        assertEquals("name must have at most " + AiModel.NAME_MAX_LENGTH + " characters", ex.getMessage());
    }

    @Test
    void update_NullModelKey_ThrowsException() {
        AiModel model = AiModel.register(new AiProviderId(providerId), "GPT-4", "gpt-4", PRICE_1, PRICE_2, 8192, 128000);
        model.clearDomainEvents();

        InvalidAiModelException ex = assertThrows(InvalidAiModelException.class,
                () -> model.update(new AiProviderId(providerId), "GPT-4", null, PRICE_1, PRICE_2, 8192, 128000, true));
        assertEquals("modelKey must not be blank", ex.getMessage());
    }

    @Test
    void update_ModelKeyExceedsMaxLength_ThrowsException() {
        AiModel model = AiModel.register(new AiProviderId(providerId), "GPT-4", "gpt-4", PRICE_1, PRICE_2, 8192, 128000);
        model.clearDomainEvents();

        String longKey = "a".repeat(AiModel.MODEL_KEY_MAX_LENGTH + 1);
        InvalidAiModelException ex = assertThrows(InvalidAiModelException.class,
                () -> model.update(new AiProviderId(providerId), "GPT-4", longKey, PRICE_1, PRICE_2, 8192, 128000, true));
        assertEquals("modelKey must have at most " + AiModel.MODEL_KEY_MAX_LENGTH + " characters", ex.getMessage());
    }

    @Test
    void update_NullInputTokenPrice_ThrowsException() {
        AiModel model = AiModel.register(new AiProviderId(providerId), "GPT-4", "gpt-4", PRICE_1, PRICE_2, 8192, 128000);
        model.clearDomainEvents();

        InvalidAiModelException ex = assertThrows(InvalidAiModelException.class,
                () -> model.update(new AiProviderId(providerId), "GPT-4", "gpt-4", null, PRICE_2, 8192, 128000, true));
        assertEquals("inputTokenPrice must not be null", ex.getMessage());
    }

    @Test
    void update_NullOutputTokenPrice_ThrowsException() {
        AiModel model = AiModel.register(new AiProviderId(providerId), "GPT-4", "gpt-4", PRICE_1, PRICE_2, 8192, 128000);
        model.clearDomainEvents();

        InvalidAiModelException ex = assertThrows(InvalidAiModelException.class,
                () -> model.update(new AiProviderId(providerId), "GPT-4", "gpt-4", PRICE_1, null, 8192, 128000, true));
        assertEquals("outputTokenPrice must not be null", ex.getMessage());
    }

    @Test
    void delete_RecordsDeletedEvent() {
        AiModel model = AiModel.register(new AiProviderId(providerId), "GPT-4", "gpt-4", PRICE_1, PRICE_2, 8192, 128000);
        model.clearDomainEvents();

        model.delete();

        List<com.mindconnect.domain.common.event.DomainEvent> events = model.domainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof AiModelDeletedEvent);
        AiModelDeletedEvent event = (AiModelDeletedEvent) events.get(0);
        assertEquals(model.id(), event.aiModelId());
    }

    @Test
    void register_WithFakeRepository_SavesAndFinds() {
        FakeAiModelRepository repo = new FakeAiModelRepository();

        AiModel model = AiModel.register(new AiProviderId(providerId), "GPT-4", "gpt-4", PRICE_1, PRICE_2, 8192, 128000);
        repo.save(model);

        var found = repo.findById(model.id());
        assertTrue(found.isPresent());
        assertEquals("GPT-4", found.get().name());
    }

    @Test
    void existsByModelKey_ReturnsTrueWhenExists() {
        FakeAiModelRepository repo = new FakeAiModelRepository();

        AiModel model = AiModel.register(new AiProviderId(providerId), "GPT-4", "gpt-4", PRICE_1, PRICE_2, 8192, 128000);
        repo.save(model);

        assertTrue(repo.existsByModelKey("gpt-4"));
    }

    @Test
    void existsByModelKey_ReturnsFalseWhenNotExists() {
        FakeAiModelRepository repo = new FakeAiModelRepository();

        assertTrue(!repo.existsByModelKey("non-existent"));
    }

    @Test
    void existsByModelKeyAndIdNot_ReturnsTrueWhenOtherHasModelKey() {
        FakeAiModelRepository repo = new FakeAiModelRepository();

        AiModel model1 = AiModel.register(new AiProviderId(providerId), "GPT-4", "gpt-4", PRICE_1, PRICE_2, 8192, 128000);
        AiModel model2 = AiModel.register(new AiProviderId(providerId), "GPT-3.5", "gpt-3.5-turbo", PRICE_1, PRICE_2, 4096, 16384);
        repo.save(model1);
        repo.save(model2);

        assertTrue(repo.existsByModelKeyAndIdNot("gpt-4", model2.id()));
    }

    @Test
    void existsByModelKeyAndIdNot_ReturnsFalseWhenSameId() {
        FakeAiModelRepository repo = new FakeAiModelRepository();

        AiModel model = AiModel.register(new AiProviderId(providerId), "GPT-4", "gpt-4", PRICE_1, PRICE_2, 8192, 128000);
        repo.save(model);

        assertTrue(!repo.existsByModelKeyAndIdNot("gpt-4", model.id()));
    }

    @Test
    void delete_RemovesFromRepository() {
        FakeAiModelRepository repo = new FakeAiModelRepository();

        AiModel model = AiModel.register(new AiProviderId(providerId), "GPT-4", "gpt-4", PRICE_1, PRICE_2, 8192, 128000);
        repo.save(model);

        model.delete();
        repo.delete(model);

        assertTrue(repo.findById(model.id()).isEmpty());
    }
}