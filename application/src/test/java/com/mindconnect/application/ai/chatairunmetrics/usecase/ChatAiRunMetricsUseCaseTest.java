package com.mindconnect.application.ai.chatairunmetrics.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mindconnect.application.ai.chatairunmetrics.command.RegisterChatAiRunMetricsCommand;
import com.mindconnect.application.ai.chatairunmetrics.command.UpdateChatAiRunMetricsCommand;
import com.mindconnect.application.ai.chatairunmetrics.dto.ChatAiRunMetricsResponse;
import com.mindconnect.application.ai.chatairunmetrics.exception.ChatAiRunMetricsAlreadyExistsApplicationException;
import com.mindconnect.application.ai.chatairunmetrics.exception.ChatAiRunMetricsNotFoundApplicationException;
import com.mindconnect.domain.ai.chatairunmetrics.FakeChatAiRunMetricsRepository;
import com.mindconnect.domain.ai.chatairunmetrics.model.aggregate.ChatAiRunMetrics;
import com.mindconnect.domain.ai.chatairun.model.valueobject.ChatAiRunId;

class RegisterChatAiRunMetricsUseCaseTest {

    private FakeChatAiRunMetricsRepository repository;
    private RegisterChatAiRunMetricsUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = new FakeChatAiRunMetricsRepository();
        useCase = new RegisterChatAiRunMetricsUseCase(repository);
    }

    @Test
    void execute_ValidCommand_ReturnsResponse() {
        UUID aiRunId = UUID.randomUUID();
        RegisterChatAiRunMetricsCommand command = new RegisterChatAiRunMetricsCommand(
                aiRunId, 100, 50, 150, new BigDecimal("0.001234"));

        ChatAiRunMetricsResponse response = useCase.execute(command);

        assertNotNull(response.id());
        assertEquals(aiRunId, response.aiRunId());
        assertEquals(100, response.promptTokens());
        assertEquals(50, response.completionTokens());
        assertEquals(150, response.totalTokens());
        assertEquals(new BigDecimal("0.001234"), response.cost());
        assertNotNull(response.createdAt());
    }

    @Test
    void execute_DuplicateAiRunId_ThrowsException() {
        UUID aiRunId = UUID.randomUUID();
        RegisterChatAiRunMetricsCommand command1 = new RegisterChatAiRunMetricsCommand(
                aiRunId, 100, 50, 150, new BigDecimal("0.001234"));
        useCase.execute(command1);

        RegisterChatAiRunMetricsCommand command2 = new RegisterChatAiRunMetricsCommand(
                aiRunId, 200, 75, 275, new BigDecimal("0.002468"));
        assertThrows(ChatAiRunMetricsAlreadyExistsApplicationException.class, () -> useCase.execute(command2));
    }
}

class GetChatAiRunMetricsByIdUseCaseTest {

    private FakeChatAiRunMetricsRepository repository;
    private GetChatAiRunMetricsByIdUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = new FakeChatAiRunMetricsRepository();
        useCase = new GetChatAiRunMetricsByIdUseCase(repository);
    }

    @Test
    void execute_ExistingId_ReturnsResponse() {
        ChatAiRunId aiRunId = new ChatAiRunId(UUID.randomUUID());
        ChatAiRunMetrics metrics = ChatAiRunMetrics.register(aiRunId, 100, 50, 150, new BigDecimal("0.001234"));
        repository.save(metrics);

        ChatAiRunMetricsResponse response = useCase.execute(metrics.id().value());

        assertEquals(metrics.id().value(), response.id());
        assertEquals(aiRunId.value(), response.aiRunId());
        assertEquals(100, response.promptTokens());
        assertEquals(50, response.completionTokens());
        assertEquals(150, response.totalTokens());
        assertEquals(new BigDecimal("0.001234"), response.cost());
    }

    @Test
    void execute_NonExistentId_ThrowsException() {
        assertThrows(ChatAiRunMetricsNotFoundApplicationException.class, () -> useCase.execute(UUID.randomUUID()));
    }
}

class ListChatAiRunMetricsUseCaseTest {

    private FakeChatAiRunMetricsRepository repository;
    private ListChatAiRunMetricsUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = new FakeChatAiRunMetricsRepository();
        useCase = new ListChatAiRunMetricsUseCase(repository);
    }

    @Test
    void execute_ReturnsAllMetrics() {
        repository.save(ChatAiRunMetrics.register(new ChatAiRunId(UUID.randomUUID()), 100, 50, 150, new BigDecimal("0.001234")));
        repository.save(ChatAiRunMetrics.register(new ChatAiRunId(UUID.randomUUID()), 200, 75, 275, new BigDecimal("0.002468")));

        List<ChatAiRunMetricsResponse> responses = useCase.execute();

        assertEquals(2, responses.size());
    }

    @Test
    void execute_EmptyRepository_ReturnsEmptyList() {
        List<ChatAiRunMetricsResponse> responses = useCase.execute();
        assertTrue(responses.isEmpty());
    }
}

class UpdateChatAiRunMetricsUseCaseTest {

    private FakeChatAiRunMetricsRepository repository;
    private UpdateChatAiRunMetricsUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = new FakeChatAiRunMetricsRepository();
        useCase = new UpdateChatAiRunMetricsUseCase(repository);
    }

    @Test
    void execute_ValidCommand_UpdatesAndReturnsResponse() {
        ChatAiRunMetrics metrics = ChatAiRunMetrics.register(new ChatAiRunId(UUID.randomUUID()), 100, 50, 150, new BigDecimal("0.001234"));
        repository.save(metrics);

        UUID newAiRunId = UUID.randomUUID();
        UpdateChatAiRunMetricsCommand command = new UpdateChatAiRunMetricsCommand(
                metrics.id().value(),
                newAiRunId,
                200,
                75,
                275,
                new BigDecimal("0.002468"));

        ChatAiRunMetricsResponse response = useCase.execute(command);

        assertEquals(newAiRunId, response.aiRunId());
        assertEquals(200, response.promptTokens());
        assertEquals(75, response.completionTokens());
        assertEquals(275, response.totalTokens());
        assertEquals(new BigDecimal("0.002468"), response.cost());
    }

    @Test
    void execute_NonExistentId_ThrowsException() {
        UpdateChatAiRunMetricsCommand command = new UpdateChatAiRunMetricsCommand(
                UUID.randomUUID(),
                UUID.randomUUID(),
                200,
                75,
                275,
                new BigDecimal("0.002468"));

        assertThrows(ChatAiRunMetricsNotFoundApplicationException.class, () -> useCase.execute(command));
    }

    @Test
    void execute_DuplicateAiRunId_ThrowsException() {
        ChatAiRunId aiRunId1 = new ChatAiRunId(UUID.randomUUID());
        ChatAiRunId aiRunId2 = new ChatAiRunId(UUID.randomUUID());
        ChatAiRunMetrics metrics1 = ChatAiRunMetrics.register(aiRunId1, 100, 50, 150, new BigDecimal("0.001234"));
        ChatAiRunMetrics metrics2 = ChatAiRunMetrics.register(aiRunId2, 200, 75, 275, new BigDecimal("0.002468"));
        repository.save(metrics1);
        repository.save(metrics2);

        UpdateChatAiRunMetricsCommand command = new UpdateChatAiRunMetricsCommand(
                metrics2.id().value(),
                aiRunId1.value(),
                300,
                100,
                400,
                new BigDecimal("0.003690"));

        assertThrows(ChatAiRunMetricsAlreadyExistsApplicationException.class, () -> useCase.execute(command));
    }
}

class DeleteChatAiRunMetricsUseCaseTest {

    private FakeChatAiRunMetricsRepository repository;
    private DeleteChatAiRunMetricsUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = new FakeChatAiRunMetricsRepository();
        useCase = new DeleteChatAiRunMetricsUseCase(repository);
    }

    @Test
    void execute_ExistingId_DeletesMetrics() {
        ChatAiRunMetrics metrics = ChatAiRunMetrics.register(new ChatAiRunId(UUID.randomUUID()), 100, 50, 150, new BigDecimal("0.001234"));
        repository.save(metrics);

        useCase.execute(metrics.id().value());

        assertTrue(repository.findById(metrics.id()).isEmpty());
    }

    @Test
    void execute_NonExistentId_ThrowsException() {
        assertThrows(ChatAiRunMetricsNotFoundApplicationException.class, () -> useCase.execute(UUID.randomUUID()));
    }
}