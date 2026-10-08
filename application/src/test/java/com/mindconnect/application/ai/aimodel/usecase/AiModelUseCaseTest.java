package com.mindconnect.application.ai.aimodel.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mindconnect.application.ai.aimodel.command.RegisterAiModelCommand;
import com.mindconnect.application.ai.aimodel.command.UpdateAiModelCommand;
import com.mindconnect.application.ai.aimodel.dto.AiModelResponse;
import com.mindconnect.application.ai.aimodel.exception.AiModelAlreadyExistsApplicationException;
import com.mindconnect.application.ai.aimodel.exception.AiModelNotFoundApplicationException;
import com.mindconnect.domain.ai.aimodel.FakeAiModelRepository;
import com.mindconnect.domain.ai.aimodel.model.aggregate.AiModel;
import com.mindconnect.domain.ai.aiprovider.model.valueobject.AiProviderId;

class RegisterAiModelUseCaseTest {

    private FakeAiModelRepository repository;
    private RegisterAiModelUseCase useCase;
    private final UUID providerId = UUID.randomUUID();
    private final BigDecimal PRICE_1 = new BigDecimal("0.00100000");
    private final BigDecimal PRICE_2 = new BigDecimal("0.00200000");

    @BeforeEach
    void setUp() {
        repository = new FakeAiModelRepository();
        useCase = new RegisterAiModelUseCase(repository);
    }

    @Test
    void execute_ValidCommand_ReturnsResponse() {
        RegisterAiModelCommand command = new RegisterAiModelCommand(
                providerId,
                "GPT-4",
                "gpt-4",
                PRICE_1,
                PRICE_2,
                8192,
                128000);

        AiModelResponse response = useCase.execute(command);

        assertNotNull(response.id());
        assertEquals(providerId, response.aiProviderId());
        assertEquals("GPT-4", response.name());
        assertEquals("gpt-4", response.modelKey());
        assertEquals(PRICE_1, response.inputTokenPrice());
        assertEquals(PRICE_2, response.outputTokenPrice());
        assertEquals(8192, response.maxTokens());
        assertEquals(128000, response.contextWindow());
        assertTrue(response.active());
        assertNotNull(response.createdAt());
        assertNotNull(response.updatedAt());
    }

    @Test
    void execute_DuplicateModelKey_ThrowsException() {
        RegisterAiModelCommand command1 = new RegisterAiModelCommand(
                providerId, "GPT-4", "gpt-4", PRICE_1, PRICE_2, 8192, 128000);
        useCase.execute(command1);

        RegisterAiModelCommand command2 = new RegisterAiModelCommand(
                providerId, "GPT-4 Turbo", "gpt-4", PRICE_1, PRICE_2, 4096, 128000);
        assertThrows(AiModelAlreadyExistsApplicationException.class, () -> useCase.execute(command2));
    }
}

class GetAiModelByIdUseCaseTest {

    private FakeAiModelRepository repository;
    private GetAiModelByIdUseCase useCase;
    private final UUID providerId = UUID.randomUUID();
    private final BigDecimal PRICE_1 = new BigDecimal("0.00100000");
    private final BigDecimal PRICE_2 = new BigDecimal("0.00200000");

    @BeforeEach
    void setUp() {
        repository = new FakeAiModelRepository();
        useCase = new GetAiModelByIdUseCase(repository);
    }

    @Test
    void execute_ExistingId_ReturnsResponse() {
        AiModel model = AiModel.register(new AiProviderId(providerId), "GPT-4", "gpt-4", PRICE_1, PRICE_2, 8192, 128000);
        repository.save(model);

        AiModelResponse response = useCase.execute(model.id().value());

        assertEquals(model.id().value(), response.id());
        assertEquals("GPT-4", response.name());
    }

    @Test
    void execute_NonExistentId_ThrowsException() {
        assertThrows(AiModelNotFoundApplicationException.class, () -> useCase.execute(UUID.randomUUID()));
    }
}

class ListAiModelUseCaseTest {

    private FakeAiModelRepository repository;
    private ListAiModelUseCase useCase;
    private final UUID providerId = UUID.randomUUID();
    private final BigDecimal PRICE_1 = new BigDecimal("0.00100000");
    private final BigDecimal PRICE_2 = new BigDecimal("0.00200000");

    @BeforeEach
    void setUp() {
        repository = new FakeAiModelRepository();
        useCase = new ListAiModelUseCase(repository);
    }

    @Test
    void execute_ReturnsAllModels() {
        repository.save(AiModel.register(new AiProviderId(providerId), "GPT-4", "gpt-4", PRICE_1, PRICE_2, 8192, 128000));
        repository.save(AiModel.register(new AiProviderId(providerId), "GPT-3.5", "gpt-3.5-turbo", PRICE_1, PRICE_2, 4096, 16384));

        List<AiModelResponse> responses = useCase.execute();

        assertEquals(2, responses.size());
    }

    @Test
    void execute_EmptyRepository_ReturnsEmptyList() {
        List<AiModelResponse> responses = useCase.execute();
        assertTrue(responses.isEmpty());
    }
}

class UpdateAiModelUseCaseTest {

    private FakeAiModelRepository repository;
    private UpdateAiModelUseCase useCase;
    private final UUID providerId = UUID.randomUUID();
    private final BigDecimal PRICE_1 = new BigDecimal("0.00100000");
    private final BigDecimal PRICE_2 = new BigDecimal("0.00200000");
    private final BigDecimal NEW_PRICE_1 = new BigDecimal("0.00150000");
    private final BigDecimal NEW_PRICE_2 = new BigDecimal("0.00300000");

    @BeforeEach
    void setUp() {
        repository = new FakeAiModelRepository();
        useCase = new UpdateAiModelUseCase(repository);
    }

    @Test
    void execute_ValidCommand_UpdatesAndReturnsResponse() {
        AiModel model = AiModel.register(new AiProviderId(providerId), "GPT-4", "gpt-4", PRICE_1, PRICE_2, 8192, 128000);
        repository.save(model);

        UpdateAiModelCommand command = new UpdateAiModelCommand(
                model.id().value(),
                providerId,
                "GPT-4 Turbo",
                "gpt-4-turbo",
                NEW_PRICE_1,
                NEW_PRICE_2,
                4096,
                128000,
                false);

        AiModelResponse response = useCase.execute(command);

        assertEquals("GPT-4 Turbo", response.name());
        assertEquals("gpt-4-turbo", response.modelKey());
        assertEquals(NEW_PRICE_1, response.inputTokenPrice());
        assertEquals(NEW_PRICE_2, response.outputTokenPrice());
        assertEquals(4096, response.maxTokens());
        assertEquals(128000, response.contextWindow());
        assertEquals(false, response.active());
    }

    @Test
    void execute_NonExistentId_ThrowsException() {
        UpdateAiModelCommand command = new UpdateAiModelCommand(
                UUID.randomUUID(),
                providerId,
                "GPT-4",
                "gpt-4",
                PRICE_1,
                PRICE_2,
                8192,
                128000,
                true);

        assertThrows(AiModelNotFoundApplicationException.class, () -> useCase.execute(command));
    }

    @Test
    void execute_DuplicateModelKey_ThrowsException() {
        AiModel model1 = AiModel.register(new AiProviderId(providerId), "GPT-4", "gpt-4", PRICE_1, PRICE_2, 8192, 128000);
        AiModel model2 = AiModel.register(new AiProviderId(providerId), "GPT-3.5", "gpt-3.5-turbo", PRICE_1, PRICE_2, 4096, 16384);
        repository.save(model1);
        repository.save(model2);

        UpdateAiModelCommand command = new UpdateAiModelCommand(
                model2.id().value(),
                providerId,
                "GPT-4",
                "gpt-4",
                PRICE_1,
                PRICE_2,
                8192,
                128000,
                true);

        assertThrows(AiModelAlreadyExistsApplicationException.class, () -> useCase.execute(command));
    }
}

class DeleteAiModelUseCaseTest {

    private FakeAiModelRepository repository;
    private DeleteAiModelUseCase useCase;
    private final UUID providerId = UUID.randomUUID();
    private final BigDecimal PRICE_1 = new BigDecimal("0.00100000");
    private final BigDecimal PRICE_2 = new BigDecimal("0.00200000");

    @BeforeEach
    void setUp() {
        repository = new FakeAiModelRepository();
        useCase = new DeleteAiModelUseCase(repository);
    }

    @Test
    void execute_ExistingId_DeletesModel() {
        AiModel model = AiModel.register(new AiProviderId(providerId), "GPT-4", "gpt-4", PRICE_1, PRICE_2, 8192, 128000);
        repository.save(model);

        useCase.execute(model.id().value());

        assertTrue(repository.findById(model.id()).isEmpty());
    }

    @Test
    void execute_NonExistentId_ThrowsException() {
        assertThrows(AiModelNotFoundApplicationException.class, () -> useCase.execute(UUID.randomUUID()));
    }
}