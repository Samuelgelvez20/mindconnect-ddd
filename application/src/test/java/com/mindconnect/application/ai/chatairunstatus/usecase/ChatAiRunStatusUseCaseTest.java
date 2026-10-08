package com.mindconnect.application.ai.chatairunstatus.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mindconnect.application.ai.chatairunstatus.command.RegisterChatAiRunStatusCommand;
import com.mindconnect.application.ai.chatairunstatus.command.UpdateChatAiRunStatusCommand;
import com.mindconnect.application.ai.chatairunstatus.dto.ChatAiRunStatusResponse;
import com.mindconnect.application.ai.chatairunstatus.exception.ChatAiRunStatusAlreadyExistsApplicationException;
import com.mindconnect.application.ai.chatairunstatus.exception.ChatAiRunStatusNotFoundApplicationException;
import com.mindconnect.domain.ai.chatairunstatus.FakeChatAiRunStatusRepository;
import com.mindconnect.domain.ai.chatairunstatus.model.aggregate.ChatAiRunStatus;

class RegisterChatAiRunStatusUseCaseTest {

    private FakeChatAiRunStatusRepository repository;
    private RegisterChatAiRunStatusUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = new FakeChatAiRunStatusRepository();
        useCase = new RegisterChatAiRunStatusUseCase(repository);
    }

    @Test
    void execute_ValidCommand_ReturnsResponse() {
        RegisterChatAiRunStatusCommand command = new RegisterChatAiRunStatusCommand("PENDING");

        ChatAiRunStatusResponse response = useCase.execute(command);

        assertNotNull(response.id());
        assertEquals("PENDING", response.name());
        assertNotNull(response.createdAt());
        assertNotNull(response.updatedAt());
    }

    @Test
    void execute_DuplicateName_ThrowsException() {
        RegisterChatAiRunStatusCommand command1 = new RegisterChatAiRunStatusCommand("PENDING");
        useCase.execute(command1);

        RegisterChatAiRunStatusCommand command2 = new RegisterChatAiRunStatusCommand("PENDING");
        assertThrows(ChatAiRunStatusAlreadyExistsApplicationException.class, () -> useCase.execute(command2));
    }
}

class GetChatAiRunStatusByIdUseCaseTest {

    private FakeChatAiRunStatusRepository repository;
    private GetChatAiRunStatusByIdUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = new FakeChatAiRunStatusRepository();
        useCase = new GetChatAiRunStatusByIdUseCase(repository);
    }

    @Test
    void execute_ExistingId_ReturnsResponse() {
        ChatAiRunStatus status = ChatAiRunStatus.register("PENDING");
        repository.save(status);

        ChatAiRunStatusResponse response = useCase.execute(status.id().value());

        assertEquals(status.id().value(), response.id());
        assertEquals("PENDING", response.name());
    }

    @Test
    void execute_NonExistentId_ThrowsException() {
        assertThrows(ChatAiRunStatusNotFoundApplicationException.class, () -> useCase.execute(UUID.randomUUID()));
    }
}

class ListChatAiRunStatusUseCaseTest {

    private FakeChatAiRunStatusRepository repository;
    private ListChatAiRunStatusUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = new FakeChatAiRunStatusRepository();
        useCase = new ListChatAiRunStatusUseCase(repository);
    }

    @Test
    void execute_ReturnsAllStatuses() {
        repository.save(ChatAiRunStatus.register("PENDING"));
        repository.save(ChatAiRunStatus.register("COMPLETED"));

        List<ChatAiRunStatusResponse> responses = useCase.execute();

        assertEquals(2, responses.size());
    }

    @Test
    void execute_EmptyRepository_ReturnsEmptyList() {
        List<ChatAiRunStatusResponse> responses = useCase.execute();
        assertTrue(responses.isEmpty());
    }
}

class UpdateChatAiRunStatusUseCaseTest {

    private FakeChatAiRunStatusRepository repository;
    private UpdateChatAiRunStatusUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = new FakeChatAiRunStatusRepository();
        useCase = new UpdateChatAiRunStatusUseCase(repository);
    }

    @Test
    void execute_ValidCommand_UpdatesAndReturnsResponse() {
        ChatAiRunStatus status = ChatAiRunStatus.register("PENDING");
        repository.save(status);

        UpdateChatAiRunStatusCommand command = new UpdateChatAiRunStatusCommand(
                status.id().value(), "COMPLETED");

        ChatAiRunStatusResponse response = useCase.execute(command);

        assertEquals("COMPLETED", response.name());
    }

    @Test
    void execute_NonExistentId_ThrowsException() {
        UpdateChatAiRunStatusCommand command = new UpdateChatAiRunStatusCommand(
                UUID.randomUUID(), "COMPLETED");

        assertThrows(ChatAiRunStatusNotFoundApplicationException.class, () -> useCase.execute(command));
    }

    @Test
    void execute_DuplicateName_ThrowsException() {
        ChatAiRunStatus status1 = ChatAiRunStatus.register("PENDING");
        ChatAiRunStatus status2 = ChatAiRunStatus.register("COMPLETED");
        repository.save(status1);
        repository.save(status2);

        UpdateChatAiRunStatusCommand command = new UpdateChatAiRunStatusCommand(
                status2.id().value(), "PENDING");

        assertThrows(ChatAiRunStatusAlreadyExistsApplicationException.class, () -> useCase.execute(command));
    }
}

class DeleteChatAiRunStatusUseCaseTest {

    private FakeChatAiRunStatusRepository repository;
    private DeleteChatAiRunStatusUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = new FakeChatAiRunStatusRepository();
        useCase = new DeleteChatAiRunStatusUseCase(repository);
    }

    @Test
    void execute_ExistingId_DeletesStatus() {
        ChatAiRunStatus status = ChatAiRunStatus.register("PENDING");
        repository.save(status);

        useCase.execute(status.id().value());

        assertTrue(repository.findById(status.id()).isEmpty());
    }

    @Test
    void execute_NonExistentId_ThrowsException() {
        assertThrows(ChatAiRunStatusNotFoundApplicationException.class, () -> useCase.execute(UUID.randomUUID()));
    }
}