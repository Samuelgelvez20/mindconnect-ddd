package com.mindconnect.application.ai.chatconversationaisettings.usecase;

import java.util.UUID;

import com.mindconnect.application.ai.chatconversationaisettings.command.RegisterChatConversationAiSettingsCommand;
import com.mindconnect.application.ai.chatconversationaisettings.dto.ChatConversationAiSettingsResponse;
import com.mindconnect.application.ai.chatconversationaisettings.exception.ChatConversationAiSettingsAlreadyExistsApplicationException;
import com.mindconnect.domain.ai.chatconversationaisettings.model.aggregate.ChatConversationAiSettings;
import com.mindconnect.domain.ai.chatconversationaisettings.model.valueobject.ChatConversationAiSettingsId;
import com.mindconnect.domain.ai.chatconversationaisettings.port.repository.ChatConversationAiSettingsRepository;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.ai.aimodel.model.valueobject.AiModelId;

public class RegisterChatConversationAiSettingsUseCase {

    private final ChatConversationAiSettingsRepository repository;

    public RegisterChatConversationAiSettingsUseCase(ChatConversationAiSettingsRepository repository) {
        this.repository = repository;
    }

    public ChatConversationAiSettingsResponse execute(RegisterChatConversationAiSettingsCommand command) {
        ChatConversationId conversationId = new ChatConversationId(command.conversationId());
        if (repository.existsByConversationId(conversationId)) {
            throw new ChatConversationAiSettingsAlreadyExistsApplicationException(command.conversationId().toString());
        }

        AiModelId defaultModelId = new AiModelId(command.defaultModelId());

        ChatConversationAiSettings settings = ChatConversationAiSettings.register(
                conversationId,
                command.aiEnabled(),
                defaultModelId);

        ChatConversationAiSettings saved = repository.save(settings);
        return ChatConversationAiSettingsResponse.from(saved);
    }
}