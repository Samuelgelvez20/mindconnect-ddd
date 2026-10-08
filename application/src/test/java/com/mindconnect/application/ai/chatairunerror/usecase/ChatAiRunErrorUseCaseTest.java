package com.mindconnect.application.ai.chatairunerror.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mindconnect.application.ai.chatairunerror.command.RegisterChatAiRunErrorCommand;
import com.mindconnect.application.ai.chatairunerror.command.UpdateChatAiRunErrorCommand;
import com.mindconnect.application.ai.chatairunerror.dto.ChatAiRunErrorResponse;
import com.mindconnect.application.ai.chatairunerror.exception.ChatAiRunErrorNotFoundApplicationException;
import com.mindconnect.domain.ai.chatairunerror.FakeChatAiRunErrorRepository;
import com.mindconnect.domain.ai.chatairunerror.model.aggregate.ChatAiRunError;
import com.mindconnect.domain.ai.chatairun.model.valueobject.ChatAiRunId;

class RegisterChatAiRunErrorUseCaseTest {

    private FakeChatAiRunErrorRepository repository;
    private RegisterChatAiRunErrorUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = new FakeChatAiRunErrorRepository();
        useCase = new RegisterChatAiRunErrorUseCase(repository);
    }

    @Test
    void execute_ValidCommand_ReturnsResponse() {
        UUID aiRunId = UUID.randomUUID();
        RegisterChatAiRunErrorCommand command = new RegisterChatAiRunErrorCommand(
                aiRunId, "Something went wrong", "ERR_001", "prov_123");

        ChatAiRunErrorResponse response = useCase.execute(command);

        assertNotNull(response.id());
        assertEquals(aiRunId, response.aiRunId());
        assertEquals("Something went wrong", response.errorMessage());
        assertEquals("ERR_001", response.errorCode());
        assertEquals("prov_123", response.providerErrorId());
        assertNotNull(response.createdAt());
    }

    @Test
    void execute_WithNullOptionalFields_ReturnsResponse() {
        UUID aiRunId = UUID.randomUUID();
        RegisterChatAiRunErrorCommand command = new RegisterChatAiRunErrorCommand(
                aiRunId, "Something went wrong", null, null);

        ChatAiRunErrorResponse response = useCase.execute(command);

        assertNotNull(response.id());
        assertEquals(aiRunId, response.aiRunId());
        assertEquals("Something went wrong", response.errorMessage());
        assertNull(response.errorCode());
        assertNull(response.providerErrorId());
    }
}

class GetChatAiRunErrorByIdUseCaseTest {

    private FakeChatAiRunErrorRepository repository;
    private GetChatAiRunErrorByIdUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = new FakeChatAiRunErrorRepository();
        useCase = new GetChatAiRunErrorByIdUseCase(repository);
    }

    @Test
    void execute_ExistingId_ReturnsResponse() {
        ChatAiRunError error = ChatAiRunError.register(new ChatAiRunId(UUID.randomUUID()), "Something went wrong", "ERR_001", "prov_123");
        repository.save(error);

        ChatAiRunErrorResponse response = useCase.execute(error.id().value());

        assertEquals(error.id().value(), response.id());
        assertEquals(error.aiRunId().value(), response.aiRunId());
        assertEquals("Something went wrong", response.errorMessage());
        assertEquals("ERR_001", response.errorCode());
        assertEquals("prov_123", response.providerErrorId());
    }

    @Test
    void execute_NonExistentId_ThrowsException() {
        assertThrows(ChatAiRunErrorNotFoundApplicationException.class, () -> useCase.execute(UUID.randomUUID()));
    }
}

class ListChatAiRunErrorUseCaseTest {

    private FakeChatAiRunErrorRepository repository;
    private ListChatAiRunErrorUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = new FakeChatAiRunErrorRepository();
        useCase = new ListChatAiRunErrorUseCase(repository);
    }

    @Test
    void execute_ReturnsAllErrors() {
        repository.save(ChatAiRunError.register(new ChatAiRunId(UUID.randomUUID()), "Error 1", "ERR_001", "prov_123"));
        repository.save(ChatAiRunError.register(new ChatAiRunId(UUID.randomUUID()), "Error 2", "ERR_002", "prov_456"));

        List<ChatAiRunErrorResponse> responses = useCase.execute();

        assertEquals(2, responses.size());
    }

    @Test
    void execute_EmptyRepository_ReturnsEmptyList() {
        List<ChatAiRunErrorResponse> responses = useCase.execute();
        assertTrue(responses.isEmpty());
    }
}

class UpdateChatAiRunErrorUseCaseTest {

    private FakeChatAiRunErrorRepository repository;
    private UpdateChatAiRunErrorUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = new FakeChatAiRunErrorRepository();
        useCase = new UpdateChatAiRunErrorUseCase(repository);
    }

    @Test
    void execute_ValidCommand_UpdatesAndReturnsResponse() {
        ChatAiRunError error = ChatAiRunError.register(new ChatAiRunId(UUID.randomUUID()), "Something went wrong", "ERR_001", "prov_123");
        repository.save(error);

        UUID newAiRunId = UUID.randomUUID();
        UpdateChatAiRunErrorCommand command = new UpdateChatAiRunErrorCommand(
                error.id().value(),
                newAiRunId,
                "Another error",
                "ERR_002",
                "prov_456");

        ChatAiRunErrorResponse response = useCase.execute(command);

        assertEquals(newAiRunId, response.aiRunId());
        assertEquals("Another error", response.errorMessage());
        assertEquals("ERR_002", response.errorCode());
        assertEquals("prov_456", response.providerErrorId());
    }

    @Test
    void execute_NonExistentId_ThrowsException() {
        UpdateChatAiRunErrorCommand command = new UpdateChatAiRunErrorCommand(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Another error",
                "ERR_002",
                "prov_456");

        assertThrows(ChatAiRunErrorNotFoundApplicationException.class, () -> useCase.execute(command));
    }

    @Test
    void execute_WithNullOptionalFields_UpdatesAndReturnsResponse() {
        ChatAiRunError error = ChatAiRunError.register(new ChatAiRunId(UUID.randomUUID()), "Something went wrong", "ERR_001", "prov_123");
        repository.save(error);

        UUID newAiRunId = UUID.randomUUID();
        UpdateChatAiRunErrorCommand command = new UpdateChatAiRunErrorCommand(
                error.id().value(),
                newAiRunId,
                "Another error",
                null,
                null);

        ChatAiRunErrorResponse response = useCase.execute(command);

        assertEquals(newAiRunId, response.aiRunId());
        assertEquals("Another error", response.errorMessage());
        assertNull(response.errorCode());
        assertNull(response.providerErrorId());
    }
}

class DeleteChatAiRunErrorUseCaseTest {

    private FakeChatAiRunErrorRepository repository;
    private DeleteChatAiRunErrorUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = new FakeChatAiRunErrorRepository();
        useCase = new DeleteChatAiRunErrorUseCase(repository);
    }

    @Test
    void execute_ExistingId_DeletesError() {
        ChatAiRunError error = ChatAiRunError.register(new ChatAiRunId(UUID.randomUUID()), "Something went wrong", "ERR_001", "prov_123");
        repository.save(error);

        useCase.execute(error.id().value());

        assertTrue(repository.findById(error.id()).isEmpty());
    }

    @Test
    void execute_NonExistentId_ThrowsException() {
        assertThrows(ChatAiRunErrorNotFoundApplicationException.class, () -> useCase.execute(UUID.randomUUID()));
    }
}