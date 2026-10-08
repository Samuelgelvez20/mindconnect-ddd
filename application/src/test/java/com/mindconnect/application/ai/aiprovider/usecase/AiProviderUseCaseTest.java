package com.mindconnect.application.ai.aiprovider.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mindconnect.application.ai.aiprovider.command.RegisterAiProviderCommand;
import com.mindconnect.application.ai.aiprovider.command.UpdateAiProviderCommand;
import com.mindconnect.application.ai.aiprovider.dto.AiProviderResponse;
import com.mindconnect.application.ai.aiprovider.exception.AiProviderAlreadyExistsApplicationException;
import com.mindconnect.application.ai.aiprovider.exception.AiProviderNotFoundApplicationException;
import com.mindconnect.domain.ai.aiprovider.FakeAiProviderRepository;
import com.mindconnect.domain.ai.aiprovider.model.aggregate.AiProvider;

class RegisterAiProviderUseCaseTest {

    private FakeAiProviderRepository repository;
    private RegisterAiProviderUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = new FakeAiProviderRepository();
        useCase = new RegisterAiProviderUseCase(repository);
    }

    @Test
    void execute_ValidCommand_ReturnsResponse() {
        RegisterAiProviderCommand command = new RegisterAiProviderCommand("OpenAI", "OpenAI, Inc.", "https://openai.com");

        AiProviderResponse response = useCase.execute(command);

        assertNotNull(response.id());
        assertEquals("OpenAI", response.name());
        assertEquals("OpenAI, Inc.", response.legalName());
        assertEquals("https://openai.com", response.website());
        assertTrue(response.active());
        assertNotNull(response.createdAt());
        assertNotNull(response.updatedAt());
    }

    @Test
    void execute_DuplicateName_ThrowsException() {
        RegisterAiProviderCommand command1 = new RegisterAiProviderCommand("OpenAI", "OpenAI, Inc.", "https://openai.com");
        useCase.execute(command1);

        RegisterAiProviderCommand command2 = new RegisterAiProviderCommand("OpenAI", "Another Corp", "https://another.com");
        assertThrows(AiProviderAlreadyExistsApplicationException.class, () -> useCase.execute(command2));
    }
}

class GetAiProviderByIdUseCaseTest {

    private FakeAiProviderRepository repository;
    private GetAiProviderByIdUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = new FakeAiProviderRepository();
        useCase = new GetAiProviderByIdUseCase(repository);
    }

    @Test
    void execute_ExistingId_ReturnsResponse() {
        AiProvider provider = AiProvider.register("OpenAI", "OpenAI, Inc.", "https://openai.com");
        repository.save(provider);

        AiProviderResponse response = useCase.execute(provider.id().value());

        assertEquals(provider.id().value(), response.id());
        assertEquals("OpenAI", response.name());
    }

    @Test
    void execute_NonExistentId_ThrowsException() {
        UUID nonExistentId = UUID.randomUUID();
        assertThrows(AiProviderNotFoundApplicationException.class, () -> useCase.execute(nonExistentId));
    }
}

class ListAiProviderUseCaseTest {

    private FakeAiProviderRepository repository;
    private ListAiProviderUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = new FakeAiProviderRepository();
        useCase = new ListAiProviderUseCase(repository);
    }

    @Test
    void execute_ReturnsAllProviders() {
        repository.save(AiProvider.register("OpenAI", "OpenAI, Inc.", "https://openai.com"));
        repository.save(AiProvider.register("Anthropic", "Anthropic, Inc.", "https://anthropic.com"));

        List<AiProviderResponse> responses = useCase.execute();

        assertEquals(2, responses.size());
    }

    @Test
    void execute_EmptyRepository_ReturnsEmptyList() {
        List<AiProviderResponse> responses = useCase.execute();
        assertTrue(responses.isEmpty());
    }
}

class UpdateAiProviderUseCaseTest {

    private FakeAiProviderRepository repository;
    private UpdateAiProviderUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = new FakeAiProviderRepository();
        useCase = new UpdateAiProviderUseCase(repository);
    }

    @Test
    void execute_ValidCommand_UpdatesAndReturnsResponse() {
        AiProvider provider = AiProvider.register("OpenAI", "OpenAI, Inc.", "https://openai.com");
        repository.save(provider);

        UpdateAiProviderCommand command = new UpdateAiProviderCommand(
                provider.id().value(),
                "OpenAI Updated",
                "OpenAI, LLC",
                "https://openai.com/updated",
                false);

        AiProviderResponse response = useCase.execute(command);

        assertEquals("OpenAI Updated", response.name());
        assertEquals("OpenAI, LLC", response.legalName());
        assertEquals("https://openai.com/updated", response.website());
        assertEquals(false, response.active());
    }

    @Test
    void execute_NonExistentId_ThrowsException() {
        UpdateAiProviderCommand command = new UpdateAiProviderCommand(
                UUID.randomUUID(),
                "OpenAI",
                "OpenAI, Inc.",
                "https://openai.com",
                true);

        assertThrows(AiProviderNotFoundApplicationException.class, () -> useCase.execute(command));
    }

    @Test
    void execute_DuplicateName_ThrowsException() {
        AiProvider provider1 = AiProvider.register("OpenAI", "OpenAI, Inc.", "https://openai.com");
        AiProvider provider2 = AiProvider.register("Anthropic", "Anthropic, Inc.", "https://anthropic.com");
        repository.save(provider1);
        repository.save(provider2);

        UpdateAiProviderCommand command = new UpdateAiProviderCommand(
                provider2.id().value(),
                "OpenAI",
                "Anthropic, Inc.",
                "https://anthropic.com",
                true);

        assertThrows(AiProviderAlreadyExistsApplicationException.class, () -> useCase.execute(command));
    }
}

class DeleteAiProviderUseCaseTest {

    private FakeAiProviderRepository repository;
    private DeleteAiProviderUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = new FakeAiProviderRepository();
        useCase = new DeleteAiProviderUseCase(repository);
    }

    @Test
    void execute_ExistingId_DeletesProvider() {
        AiProvider provider = AiProvider.register("OpenAI", "OpenAI, Inc.", "https://openai.com");
        repository.save(provider);

        useCase.execute(provider.id().value());

        assertTrue(repository.findById(provider.id()).isEmpty());
    }

    @Test
    void execute_NonExistentId_ThrowsException() {
        assertThrows(AiProviderNotFoundApplicationException.class, () -> useCase.execute(UUID.randomUUID()));
    }
}