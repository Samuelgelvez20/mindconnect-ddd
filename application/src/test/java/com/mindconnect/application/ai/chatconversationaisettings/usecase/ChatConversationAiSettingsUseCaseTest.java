package com.mindconnect.application.ai.chatconversationaisettings.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mindconnect.application.ai.chatconversationaisettings.command.RegisterChatConversationAiSettingsCommand;
import com.mindconnect.application.ai.chatconversationaisettings.command.UpdateChatConversationAiSettingsCommand;
import com.mindconnect.application.ai.chatconversationaisettings.dto.ChatConversationAiSettingsResponse;
import com.mindconnect.application.ai.chatconversationaisettings.exception.ChatConversationAiSettingsAlreadyExistsApplicationException;
import com.mindconnect.application.ai.chatconversationaisettings.exception.ChatConversationAiSettingsNotFoundApplicationException;
import com.mindconnect.domain.ai.chatconversationaisettings.FakeChatConversationAiSettingsRepository;
import com.mindconnect.domain.ai.chatconversationaisettings.model.aggregate.ChatConversationAiSettings;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.ai.aimodel.model.valueobject.AiModelId;

class RegisterChatConversationAiSettingsUseCaseTest {

    private FakeChatConversationAiSettingsRepository repository;
    private RegisterChatConversationAiSettingsUseCase useCase;
    private final UUID conversationId = UUID.randomUUID();
    private final UUID modelId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        repository = new FakeChatConversationAiSettingsRepository();
        useCase = new RegisterChatConversationAiSettingsUseCase(repository);
    }

    @Test
    void execute_ValidCommand_ReturnsResponse() {
        RegisterChatConversationAiSettingsCommand command = new RegisterChatConversationAiSettingsCommand(
                conversationId, true, modelId);

        ChatConversationAiSettingsResponse response = useCase.execute(command);

        assertNotNull(response.id());
        assertEquals(conversationId, response.conversationId());
        assertEquals(true, response.aiEnabled());
        assertEquals(modelId, response.defaultModelId());
        assertNotNull(response.createdAt());
        assertNotNull(response.updatedAt());
    }

    @Test
    void execute_DuplicateConversationId_ThrowsException() {
        RegisterChatConversationAiSettingsCommand command1 = new RegisterChatConversationAiSettingsCommand(
                conversationId, true, modelId);
        useCase.execute(command1);

        RegisterChatConversationAiSettingsCommand command2 = new RegisterChatConversationAiSettingsCommand(
                conversationId, false, UUID.randomUUID());
        assertThrows(ChatConversationAiSettingsAlreadyExistsApplicationException.class, () -> useCase.execute(command2));
    }
}

class GetChatConversationAiSettingsByIdUseCaseTest {

    private FakeChatConversationAiSettingsRepository repository;
    private GetChatConversationAiSettingsByIdUseCase useCase;
    private final UUID conversationId = UUID.randomUUID();
    private final UUID modelId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        repository = new FakeChatConversationAiSettingsRepository();
        useCase = new GetChatConversationAiSettingsByIdUseCase(repository);
    }

    @Test
    void execute_ExistingId_ReturnsResponse() {
        ChatConversationAiSettings settings = ChatConversationAiSettings.register(
                new ChatConversationId(conversationId), true, new AiModelId(modelId));
        repository.save(settings);

        ChatConversationAiSettingsResponse response = useCase.execute(settings.id().value());

        assertEquals(settings.id().value(), response.id());
        assertEquals(conversationId, response.conversationId());
    }

    @Test
    void execute_NonExistentId_ThrowsException() {
        assertThrows(ChatConversationAiSettingsNotFoundApplicationException.class, () -> useCase.execute(UUID.randomUUID()));
    }
}

class ListChatConversationAiSettingsUseCaseTest {

    private FakeChatConversationAiSettingsRepository repository;
    private ListChatConversationAiSettingsUseCase useCase;
    private final UUID conversationId1 = UUID.randomUUID();
    private final UUID conversationId2 = UUID.randomUUID();
    private final UUID modelId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        repository = new FakeChatConversationAiSettingsRepository();
        useCase = new ListChatConversationAiSettingsUseCase(repository);
    }

    @Test
    void execute_ReturnsAllSettings() {
        repository.save(ChatConversationAiSettings.register(
                new ChatConversationId(conversationId1), true, new AiModelId(modelId)));
        repository.save(ChatConversationAiSettings.register(
                new ChatConversationId(conversationId2), false, new AiModelId(modelId)));

        List<ChatConversationAiSettingsResponse> responses = useCase.execute();

        assertEquals(2, responses.size());
    }

    @Test
    void execute_EmptyRepository_ReturnsEmptyList() {
        List<ChatConversationAiSettingsResponse> responses = useCase.execute();
        assertTrue(responses.isEmpty());
    }
}

class UpdateChatConversationAiSettingsUseCaseTest {

    private FakeChatConversationAiSettingsRepository repository;
    private UpdateChatConversationAiSettingsUseCase useCase;
    private final UUID conversationId = UUID.randomUUID();
    private final UUID modelId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        repository = new FakeChatConversationAiSettingsRepository();
        useCase = new UpdateChatConversationAiSettingsUseCase(repository);
    }

    @Test
    void execute_ValidCommand_UpdatesAndReturnsResponse() {
        ChatConversationAiSettings settings = ChatConversationAiSettings.register(
                new ChatConversationId(conversationId), true, new AiModelId(modelId));
        repository.save(settings);

        UUID newModelId = UUID.randomUUID();
        UpdateChatConversationAiSettingsCommand command = new UpdateChatConversationAiSettingsCommand(
                settings.id().value(), false, newModelId);

        ChatConversationAiSettingsResponse response = useCase.execute(command);

        assertEquals(false, response.aiEnabled());
        assertEquals(newModelId, response.defaultModelId());
    }

    @Test
    void execute_NonExistentId_ThrowsException() {
        UpdateChatConversationAiSettingsCommand command = new UpdateChatConversationAiSettingsCommand(
                UUID.randomUUID(), true, UUID.randomUUID());

        assertThrows(ChatConversationAiSettingsNotFoundApplicationException.class, () -> useCase.execute(command));
    }
}

class DeleteChatConversationAiSettingsUseCaseTest {

    private FakeChatConversationAiSettingsRepository repository;
    private DeleteChatConversationAiSettingsUseCase useCase;
    private final UUID conversationId = UUID.randomUUID();
    private final UUID modelId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        repository = new FakeChatConversationAiSettingsRepository();
        useCase = new DeleteChatConversationAiSettingsUseCase(repository);
    }

    @Test
    void execute_ExistingId_DeletesSettings() {
        ChatConversationAiSettings settings = ChatConversationAiSettings.register(
                new ChatConversationId(conversationId), true, new AiModelId(modelId));
        repository.save(settings);

        useCase.execute(settings.id().value());

        assertTrue(repository.findById(settings.id()).isEmpty());
    }

    @Test
    void execute_NonExistentId_ThrowsException() {
        assertThrows(ChatConversationAiSettingsNotFoundApplicationException.class, () -> useCase.execute(UUID.randomUUID()));
    }
}