package com.mindconnect.domain.ai.aiprovider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mindconnect.domain.ai.aiprovider.event.AiProviderDeletedEvent;
import com.mindconnect.domain.ai.aiprovider.event.AiProviderRegisteredEvent;
import com.mindconnect.domain.ai.aiprovider.event.AiProviderUpdatedEvent;
import com.mindconnect.domain.ai.aiprovider.exception.InvalidAiProviderException;
import com.mindconnect.domain.ai.aiprovider.model.aggregate.AiProvider;
import com.mindconnect.domain.ai.aiprovider.model.valueobject.AiProviderId;

class AiProviderTest {

    @Test
    void register_ValidData_CreatesProviderAndRecordsEvent() {
        String name = "OpenAI";
        String legalName = "OpenAI, Inc.";
        String website = "https://openai.com";

        AiProvider provider = AiProvider.register(name, legalName, website);

        assertNotNull(provider.id());
        assertEquals("OpenAI", provider.name());
        assertEquals("OpenAI, Inc.", provider.legalName());
        assertEquals("https://openai.com", provider.website());
        assertTrue(provider.active());
        assertNotNull(provider.createdAt());
        assertNotNull(provider.updatedAt());

        List<com.mindconnect.domain.common.event.DomainEvent> events = provider.domainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof AiProviderRegisteredEvent);
        AiProviderRegisteredEvent event = (AiProviderRegisteredEvent) events.get(0);
        assertEquals(provider.id(), event.aiProviderId());
    }

    @Test
    void register_TrimsName() {
        AiProvider provider = AiProvider.register("  OpenAI  ", "Legal", "https://openai.com");
        assertEquals("OpenAI", provider.name());
    }

    @Test
    void register_NullName_ThrowsException() {
        InvalidAiProviderException ex = assertThrows(InvalidAiProviderException.class,
                () -> AiProvider.register(null, "Legal", "https://openai.com"));
        assertEquals("name must not be blank", ex.getMessage());
    }

    @Test
    void register_BlankName_ThrowsException() {
        InvalidAiProviderException ex = assertThrows(InvalidAiProviderException.class,
                () -> AiProvider.register("   ", "Legal", "https://openai.com"));
        assertEquals("name must not be blank", ex.getMessage());
    }

    @Test
    void register_NameExceedsMaxLength_ThrowsException() {
        String longName = "a".repeat(AiProvider.NAME_MAX_LENGTH + 1);
        InvalidAiProviderException ex = assertThrows(InvalidAiProviderException.class,
                () -> AiProvider.register(longName, "Legal", "https://openai.com"));
        assertEquals("name must have at most " + AiProvider.NAME_MAX_LENGTH + " characters", ex.getMessage());
    }

    @Test
    void register_LegalNameExceedsMaxLength_ThrowsException() {
        String longLegalName = "a".repeat(AiProvider.LEGAL_NAME_MAX_LENGTH + 1);
        InvalidAiProviderException ex = assertThrows(InvalidAiProviderException.class,
                () -> AiProvider.register("OpenAI", longLegalName, "https://openai.com"));
        assertEquals("legalName must have at most " + AiProvider.LEGAL_NAME_MAX_LENGTH + " characters", ex.getMessage());
    }

    @Test
    void register_NullLegalName_SetsNull() {
        AiProvider provider = AiProvider.register("OpenAI", null, "https://openai.com");
        assertEquals(null, provider.legalName());
    }

    @Test
    void register_BlankLegalName_SetsNull() {
        AiProvider provider = AiProvider.register("OpenAI", "   ", "https://openai.com");
        assertEquals(null, provider.legalName());
    }

    @Test
    void register_NullWebsite_SetsNull() {
        AiProvider provider = AiProvider.register("OpenAI", "Legal", null);
        assertEquals(null, provider.website());
    }

    @Test
    void register_BlankWebsite_SetsNull() {
        AiProvider provider = AiProvider.register("OpenAI", "Legal", "   ");
        assertEquals(null, provider.website());
    }

    @Test
    void restore_DoesNotValidateAndDoesNotRecordEvents() {
        AiProviderId id = AiProviderId.generate();
        Instant createdAt = Instant.now().minusSeconds(100);
        Instant updatedAt = Instant.now().minusSeconds(50);

        AiProvider provider = AiProvider.restore(id, "OpenAI", "OpenAI, Inc.", "https://openai.com", true, createdAt, updatedAt);

        assertEquals(id, provider.id());
        assertEquals("OpenAI", provider.name());
        assertEquals("OpenAI, Inc.", provider.legalName());
        assertEquals("https://openai.com", provider.website());
        assertTrue(provider.active());
        assertEquals(createdAt, provider.createdAt());
        assertEquals(updatedAt, provider.updatedAt());
        assertTrue(provider.domainEvents().isEmpty());
    }

    @Test
    void update_ValidData_UpdatesFieldsAndRecordsEvent() {
        AiProvider provider = AiProvider.register("OpenAI", "OpenAI, Inc.", "https://openai.com");
        provider.clearDomainEvents();

        provider.update("OpenAI Updated", "OpenAI, LLC", "https://openai.com/updated", false);

        assertEquals("OpenAI Updated", provider.name());
        assertEquals("OpenAI, LLC", provider.legalName());
        assertEquals("https://openai.com/updated", provider.website());
        assertEquals(false, provider.active());

        List<com.mindconnect.domain.common.event.DomainEvent> events = provider.domainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof AiProviderUpdatedEvent);
        AiProviderUpdatedEvent event = (AiProviderUpdatedEvent) events.get(0);
        assertEquals(provider.id(), event.aiProviderId());
        assertEquals("OpenAI Updated", event.name());
        assertEquals("OpenAI, LLC", event.legalName());
        assertEquals("https://openai.com/updated", event.website());
        assertEquals(false, event.active());
    }

    @Test
    void update_TrimsFields() {
        AiProvider provider = AiProvider.register("OpenAI", "Legal", "https://openai.com");
        provider.clearDomainEvents();

        provider.update("  OpenAI Updated  ", "  Legal Updated  ", "  https://openai.com  ", true);

        assertEquals("OpenAI Updated", provider.name());
        assertEquals("Legal Updated", provider.legalName());
        assertEquals("https://openai.com", provider.website());
    }

    @Test
    void update_NullName_ThrowsException() {
        AiProvider provider = AiProvider.register("OpenAI", "Legal", "https://openai.com");
        provider.clearDomainEvents();

        InvalidAiProviderException ex = assertThrows(InvalidAiProviderException.class,
                () -> provider.update(null, "Legal", "https://openai.com", true));
        assertEquals("name must not be blank", ex.getMessage());
    }

    @Test
    void update_NameExceedsMaxLength_ThrowsException() {
        AiProvider provider = AiProvider.register("OpenAI", "Legal", "https://openai.com");
        provider.clearDomainEvents();

        String longName = "a".repeat(AiProvider.NAME_MAX_LENGTH + 1);
        InvalidAiProviderException ex = assertThrows(InvalidAiProviderException.class,
                () -> provider.update(longName, "Legal", "https://openai.com", true));
        assertEquals("name must have at most " + AiProvider.NAME_MAX_LENGTH + " characters", ex.getMessage());
    }

    @Test
    void update_LegalNameExceedsMaxLength_ThrowsException() {
        AiProvider provider = AiProvider.register("OpenAI", "Legal", "https://openai.com");
        provider.clearDomainEvents();

        String longLegalName = "a".repeat(AiProvider.LEGAL_NAME_MAX_LENGTH + 1);
        InvalidAiProviderException ex = assertThrows(InvalidAiProviderException.class,
                () -> provider.update("OpenAI", longLegalName, "https://openai.com", true));
        assertEquals("legalName must have at most " + AiProvider.LEGAL_NAME_MAX_LENGTH + " characters", ex.getMessage());
    }

    @Test
    void delete_RecordsDeletedEvent() {
        AiProvider provider = AiProvider.register("OpenAI", "Legal", "https://openai.com");
        provider.clearDomainEvents();

        provider.delete();

        List<com.mindconnect.domain.common.event.DomainEvent> events = provider.domainEvents();
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof AiProviderDeletedEvent);
        AiProviderDeletedEvent event = (AiProviderDeletedEvent) events.get(0);
        assertEquals(provider.id(), event.aiProviderId());
    }

    @Test
    void register_WithFakeRepository_SavesAndFinds() {
        FakeAiProviderRepository repo = new FakeAiProviderRepository();

        AiProvider provider = AiProvider.register("OpenAI", "OpenAI, Inc.", "https://openai.com");
        repo.save(provider);

        var found = repo.findById(provider.id());
        assertTrue(found.isPresent());
        assertEquals("OpenAI", found.get().name());
    }

    @Test
    void existsByName_ReturnsTrueWhenExists() {
        FakeAiProviderRepository repo = new FakeAiProviderRepository();

        AiProvider provider = AiProvider.register("OpenAI", "Legal", "https://openai.com");
        repo.save(provider);

        assertTrue(repo.existsByName("OpenAI"));
    }

    @Test
    void existsByName_ReturnsFalseWhenNotExists() {
        FakeAiProviderRepository repo = new FakeAiProviderRepository();

        assertTrue(!repo.existsByName("NonExistent"));
    }

    @Test
    void existsByNameAndIdNot_ReturnsTrueWhenOtherHasName() {
        FakeAiProviderRepository repo = new FakeAiProviderRepository();

        AiProvider provider1 = AiProvider.register("OpenAI", "Legal", "https://openai.com");
        AiProvider provider2 = AiProvider.register("Anthropic", "Legal", "https://anthropic.com");
        repo.save(provider1);
        repo.save(provider2);

        assertTrue(repo.existsByNameAndIdNot("OpenAI", provider2.id()));
    }

    @Test
    void existsByNameAndIdNot_ReturnsFalseWhenSameId() {
        FakeAiProviderRepository repo = new FakeAiProviderRepository();

        AiProvider provider = AiProvider.register("OpenAI", "Legal", "https://openai.com");
        repo.save(provider);

        assertTrue(!repo.existsByNameAndIdNot("OpenAI", provider.id()));
    }

    @Test
    void delete_RemovesFromRepository() {
        FakeAiProviderRepository repo = new FakeAiProviderRepository();

        AiProvider provider = AiProvider.register("OpenAI", "Legal", "https://openai.com");
        repo.save(provider);

        provider.delete();
        repo.delete(provider);

        assertTrue(repo.findById(provider.id()).isEmpty());
    }
}