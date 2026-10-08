package com.mindconnect.infrastructure.ai.chatconversationaisettings.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.ai.chatconversationaisettings.usecase.DeleteChatConversationAiSettingsUseCase;
import com.mindconnect.application.ai.chatconversationaisettings.usecase.GetChatConversationAiSettingsByIdUseCase;
import com.mindconnect.application.ai.chatconversationaisettings.usecase.ListChatConversationAiSettingsUseCase;
import com.mindconnect.application.ai.chatconversationaisettings.usecase.RegisterChatConversationAiSettingsUseCase;
import com.mindconnect.application.ai.chatconversationaisettings.usecase.UpdateChatConversationAiSettingsUseCase;
import com.mindconnect.domain.ai.chatconversationaisettings.port.repository.ChatConversationAiSettingsRepository;
import com.mindconnect.infrastructure.ai.chatconversationaisettings.adapters.out.persistence.mappers.ChatConversationAiSettingsPersistenceMapper;
import com.mindconnect.infrastructure.ai.chatconversationaisettings.adapters.out.persistence.repositories.ChatConversationAiSettingsJpaRepository;
import com.mindconnect.infrastructure.ai.chatconversationaisettings.adapters.out.persistence.repositories.ChatConversationAiSettingsRepositoryAdapter;

@Configuration
class ChatConversationAiSettingsBeansConfig {

    @Bean
    ChatConversationAiSettingsPersistenceMapper chatConversationAiSettingsPersistenceMapper() {
        return new ChatConversationAiSettingsPersistenceMapper();
    }

    @Bean
    ChatConversationAiSettingsRepository chatConversationAiSettingsRepository(
            ChatConversationAiSettingsJpaRepository jpaRepository,
            ChatConversationAiSettingsPersistenceMapper mapper) {
        return new ChatConversationAiSettingsRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    RegisterChatConversationAiSettingsUseCase registerChatConversationAiSettingsUseCase(ChatConversationAiSettingsRepository repository) {
        return new RegisterChatConversationAiSettingsUseCase(repository);
    }

    @Bean
    GetChatConversationAiSettingsByIdUseCase getChatConversationAiSettingsByIdUseCase(ChatConversationAiSettingsRepository repository) {
        return new GetChatConversationAiSettingsByIdUseCase(repository);
    }

    @Bean
    ListChatConversationAiSettingsUseCase listChatConversationAiSettingsUseCase(ChatConversationAiSettingsRepository repository) {
        return new ListChatConversationAiSettingsUseCase(repository);
    }

    @Bean
    UpdateChatConversationAiSettingsUseCase updateChatConversationAiSettingsUseCase(ChatConversationAiSettingsRepository repository) {
        return new UpdateChatConversationAiSettingsUseCase(repository);
    }

    @Bean
    DeleteChatConversationAiSettingsUseCase deleteChatConversationAiSettingsUseCase(ChatConversationAiSettingsRepository repository) {
        return new DeleteChatConversationAiSettingsUseCase(repository);
    }
}