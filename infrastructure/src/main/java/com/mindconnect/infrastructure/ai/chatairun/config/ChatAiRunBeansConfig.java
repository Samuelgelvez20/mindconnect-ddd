package com.mindconnect.infrastructure.ai.chatairun.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.ai.chatairun.usecase.DeleteChatAiRunUseCase;
import com.mindconnect.application.ai.chatairun.usecase.GetChatAiRunByIdUseCase;
import com.mindconnect.application.ai.chatairun.usecase.ListChatAiRunUseCase;
import com.mindconnect.application.ai.chatairun.usecase.RegisterChatAiRunUseCase;
import com.mindconnect.application.ai.chatairun.usecase.UpdateChatAiRunUseCase;
import com.mindconnect.domain.ai.chatairun.port.repository.ChatAiRunRepository;
import com.mindconnect.infrastructure.ai.chatairun.adapters.out.persistence.mappers.ChatAiRunPersistenceMapper;
import com.mindconnect.infrastructure.ai.chatairun.adapters.out.persistence.repositories.ChatAiRunJpaRepository;
import com.mindconnect.infrastructure.ai.chatairun.adapters.out.persistence.repositories.ChatAiRunRepositoryAdapter;

@Configuration
class ChatAiRunBeansConfig {

    @Bean
    ChatAiRunPersistenceMapper chatAiRunPersistenceMapper() {
        return new ChatAiRunPersistenceMapper();
    }

    @Bean
    ChatAiRunRepository chatAiRunRepository(
            ChatAiRunJpaRepository jpaRepository,
            ChatAiRunPersistenceMapper mapper) {
        return new ChatAiRunRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    RegisterChatAiRunUseCase registerChatAiRunUseCase(ChatAiRunRepository repository) {
        return new RegisterChatAiRunUseCase(repository);
    }

    @Bean
    GetChatAiRunByIdUseCase getChatAiRunByIdUseCase(ChatAiRunRepository repository) {
        return new GetChatAiRunByIdUseCase(repository);
    }

    @Bean
    ListChatAiRunUseCase listChatAiRunUseCase(ChatAiRunRepository repository) {
        return new ListChatAiRunUseCase(repository);
    }

    @Bean
    UpdateChatAiRunUseCase updateChatAiRunUseCase(ChatAiRunRepository repository) {
        return new UpdateChatAiRunUseCase(repository);
    }

    @Bean
    DeleteChatAiRunUseCase deleteChatAiRunUseCase(ChatAiRunRepository repository) {
        return new DeleteChatAiRunUseCase(repository);
    }
}