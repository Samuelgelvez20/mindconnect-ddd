package com.mindconnect.application.ai.chatconversationaisettings.usecase;

import java.util.UUID;

import com.mindconnect.application.ai.chatconversationaisettings.command.UpdateChatConversationAiSettingsCommand;
import com.mindconnect.application.ai.chatconversationaisettings.dto.ChatConversationAiSettingsResponse;
import com.mindconnect.application.ai.chatconversationaisettings.exception.ChatConversationAiSettingsAlreadyExistsApplicationException;
import com.mindconnect.application.ai.chatconversationaisettings.exception.ChatConversationAiSettingsNotFoundApplicationException;
import com.mindconnect.domain.ai.chatconversationaisettings.model.aggregate.ChatConversationAiSettings;
import com.mindconnect.domain.ai.chatconversationaisettings.model.valueobject.ChatConversationAiSettingsId;
import com.mindconnect.domain.ai.aimodel.model.valueobject.AiModelId;
import com.mindconnect.domain.ai.chatconversationaisettings.port.repository.ChatConversationAiSettingsRepository;

public class UpdateChatConversationAiSettingsUseCase {

    private final ChatConversationAiSettingsRepository repository;

    public UpdateChatConversationAiSettingsUseCase(ChatConversationAiSettingsRepository repository) {
        this.repository = repository;
    }

    public ChatConversationAiSettingsResponse execute(UpdateChatConversationAiSettingsCommand command) {
        ChatConversationAiSettingsId settingsId = new ChatConversationAiSettingsId(command.id());
        ChatConversationAiSettings settings = repository.findById(settingsId)
                .orElseThrow(() -> new ChatConversationAiSettingsNotFoundApplicationException(command.id().toString()));

        AiModelId defaultModelId = new AiModelId(command.defaultModelId());
        settings.update(command.aiEnabled(), defaultModelId);

        ChatConversationAiSettings saved = repository.save(settings);
        return ChatConversationAiSettingsResponse.from(saved);
    }
}