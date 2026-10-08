package com.mindconnect.application.ai.chatairun.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mindconnect.application.ai.chatairun.command.RegisterChatAiRunCommand;
import com.mindconnect.application.ai.chatairun.command.UpdateChatAiRunCommand;
import com.mindconnect.application.ai.chatairun.dto.ChatAiRunResponse;
import com.mindconnect.application.ai.chatairun.exception.ChatAiRunNotFoundApplicationException;
import com.mindconnect.domain.ai.chatairun.FakeChatAiRunRepository;
import com.mindconnect.domain.ai.chatairun.model.aggregate.ChatAiRun;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.chatmessage.model.valueobject.ChatMessageId;
import com.mindconnect.domain.ai.aimodel.model.valueobject.AiModelId;
import com.mindconnect.domain.ai.chatairunstatus.model.valueobject.ChatAiRunStatusId;

class RegisterChatAiRunUseCaseTest {

    private FakeChatAiRunRepository repository;
    private RegisterChatAiRunUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = new FakeChatAiRunRepository();
        useCase = new RegisterChatAiRunUseCase(repository);
    }

    @Test
    void execute_ValidCommand_ReturnsResponse() {
        UUID conversationId = UUID.randomUUID();
        UUID messageId = UUID.randomUUID();
        UUID modelId = UUID.randomUUID();
        UUID aiRunStatusId = UUID.randomUUID();

        RegisterChatAiRunCommand command = new RegisterChatAiRunCommand(conversationId, messageId, modelId, aiRunStatusId);

        ChatAiRunResponse response = useCase.execute(command);

        assertNotNull(response.id());
        assertEquals(conversationId, response.conversationId());
        assertEquals(messageId, response.messageId());
        assertEquals(modelId, response.modelId());
        assertEquals(aiRunStatusId, response.aiRunStatusId());
        assertNotNull(response.createdAt());
        assertNotNull(response.updatedAt());
    }
}

class GetChatAiRunByIdUseCaseTest {

    private FakeChatAiRunRepository repository;
    private GetChatAiRunByIdUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = new FakeChatAiRunRepository();
        useCase = new GetChatAiRunByIdUseCase(repository);
    }

    @Test
    void execute_ExistingId_ReturnsResponse() {
        ChatAiRun run = ChatAiRun.register(
                new ChatConversationId(UUID.randomUUID()),
                new ChatMessageId(UUID.randomUUID()),
                new AiModelId(UUID.randomUUID()),
                new ChatAiRunStatusId(UUID.randomUUID()));
        repository.save(run);

        ChatAiRunResponse response = useCase.execute(run.id().value());

        assertEquals(run.id().value(), response.id());
        assertEquals(run.conversationId().value(), response.conversationId());
        assertEquals(run.messageId().value(), response.messageId());
        assertEquals(run.modelId().value(), response.modelId());
        assertEquals(run.aiRunStatusId().value(), response.aiRunStatusId());
    }

    @Test
    void execute_NonExistentId_ThrowsException() {
        assertThrows(ChatAiRunNotFoundApplicationException.class, () -> useCase.execute(UUID.randomUUID()));
    }
}

class ListChatAiRunUseCaseTest {

    private FakeChatAiRunRepository repository;
    private ListChatAiRunUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = new FakeChatAiRunRepository();
        useCase = new ListChatAiRunUseCase(repository);
    }

    @Test
    void execute_ReturnsAllRuns() {
        repository.save(ChatAiRun.register(
                new ChatConversationId(UUID.randomUUID()),
                new ChatMessageId(UUID.randomUUID()),
                new AiModelId(UUID.randomUUID()),
                new ChatAiRunStatusId(UUID.randomUUID())));
        repository.save(ChatAiRun.register(
                new ChatConversationId(UUID.randomUUID()),
                new ChatMessageId(UUID.randomUUID()),
                new AiModelId(UUID.randomUUID()),
                new ChatAiRunStatusId(UUID.randomUUID())));

        List<ChatAiRunResponse> responses = useCase.execute();

        assertEquals(2, responses.size());
    }

    @Test
    void execute_EmptyRepository_ReturnsEmptyList() {
        List<ChatAiRunResponse> responses = useCase.execute();
        assertTrue(responses.isEmpty());
    }
}

class UpdateChatAiRunUseCaseTest {

    private FakeChatAiRunRepository repository;
    private UpdateChatAiRunUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = new FakeChatAiRunRepository();
        useCase = new UpdateChatAiRunUseCase(repository);
    }

    @Test
    void execute_ValidCommand_UpdatesAndReturnsResponse() {
        ChatAiRun run = ChatAiRun.register(
                new ChatConversationId(UUID.randomUUID()),
                new ChatMessageId(UUID.randomUUID()),
                new AiModelId(UUID.randomUUID()),
                new ChatAiRunStatusId(UUID.randomUUID()));
        repository.save(run);

        UUID newConversationId = UUID.randomUUID();
        UUID newMessageId = UUID.randomUUID();
        UUID newModelId = UUID.randomUUID();
        UUID newAiRunStatusId = UUID.randomUUID();

        UpdateChatAiRunCommand command = new UpdateChatAiRunCommand(
                run.id().value(),
                newConversationId,
                newMessageId,
                newModelId,
                newAiRunStatusId);

        ChatAiRunResponse response = useCase.execute(command);

        assertEquals(newConversationId, response.conversationId());
        assertEquals(newMessageId, response.messageId());
        assertEquals(newModelId, response.modelId());
        assertEquals(newAiRunStatusId, response.aiRunStatusId());
    }

    @Test
    void execute_NonExistentId_ThrowsException() {
        UUID conversationId = UUID.randomUUID();
        UUID messageId = UUID.randomUUID();
        UUID modelId = UUID.randomUUID();
        UUID aiRunStatusId = UUID.randomUUID();

        UpdateChatAiRunCommand command = new UpdateChatAiRunCommand(
                UUID.randomUUID(),
                conversationId,
                messageId,
                modelId,
                aiRunStatusId);

        assertThrows(ChatAiRunNotFoundApplicationException.class, () -> useCase.execute(command));
    }
}

class DeleteChatAiRunUseCaseTest {

    private FakeChatAiRunRepository repository;
    private DeleteChatAiRunUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = new FakeChatAiRunRepository();
        useCase = new DeleteChatAiRunUseCase(repository);
    }

    @Test
    void execute_ExistingId_DeletesRun() {
        ChatAiRun run = ChatAiRun.register(
                new ChatConversationId(UUID.randomUUID()),
                new ChatMessageId(UUID.randomUUID()),
                new AiModelId(UUID.randomUUID()),
                new ChatAiRunStatusId(UUID.randomUUID()));
        repository.save(run);

        useCase.execute(run.id().value());

        assertTrue(repository.findById(run.id()).isEmpty());
    }

    @Test
    void execute_NonExistentId_ThrowsException() {
        assertThrows(ChatAiRunNotFoundApplicationException.class, () -> useCase.execute(UUID.randomUUID()));
    }
}