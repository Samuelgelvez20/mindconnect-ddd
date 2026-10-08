package com.mindconnect.infrastructure.ai.chatairunerror.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.ai.chatairunerror.usecase.DeleteChatAiRunErrorUseCase;
import com.mindconnect.application.ai.chatairunerror.usecase.GetChatAiRunErrorByIdUseCase;
import com.mindconnect.application.ai.chatairunerror.usecase.ListChatAiRunErrorUseCase;
import com.mindconnect.application.ai.chatairunerror.usecase.RegisterChatAiRunErrorUseCase;
import com.mindconnect.application.ai.chatairunerror.usecase.UpdateChatAiRunErrorUseCase;
import com.mindconnect.domain.ai.chatairunerror.port.repository.ChatAiRunErrorRepository;
import com.mindconnect.infrastructure.ai.chatairunerror.adapters.out.persistence.mappers.ChatAiRunErrorPersistenceMapper;
import com.mindconnect.infrastructure.ai.chatairunerror.adapters.out.persistence.repositories.ChatAiRunErrorJpaRepository;
import com.mindconnect.infrastructure.ai.chatairunerror.adapters.out.persistence.repositories.ChatAiRunErrorRepositoryAdapter;

@Configuration
class ChatAiRunErrorBeansConfig {

    @Bean
    ChatAiRunErrorPersistenceMapper chatAiRunErrorPersistenceMapper() {
        return new ChatAiRunErrorPersistenceMapper();
    }

    @Bean
    ChatAiRunErrorRepository chatAiRunErrorRepository(
            ChatAiRunErrorJpaRepository jpaRepository,
            ChatAiRunErrorPersistenceMapper mapper) {
        return new ChatAiRunErrorRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    RegisterChatAiRunErrorUseCase registerChatAiRunErrorUseCase(ChatAiRunErrorRepository repository) {
        return new RegisterChatAiRunErrorUseCase(repository);
    }

    @Bean
    GetChatAiRunErrorByIdUseCase getChatAiRunErrorByIdUseCase(ChatAiRunErrorRepository repository) {
        return new GetChatAiRunErrorByIdUseCase(repository);
    }

    @Bean
    ListChatAiRunErrorUseCase listChatAiRunErrorUseCase(ChatAiRunErrorRepository repository) {
        return new ListChatAiRunErrorUseCase(repository);
    }

    @Bean
    UpdateChatAiRunErrorUseCase updateChatAiRunErrorUseCase(ChatAiRunErrorRepository repository) {
        return new UpdateChatAiRunErrorUseCase(repository);
    }

    @Bean
    DeleteChatAiRunErrorUseCase deleteChatAiRunErrorUseCase(ChatAiRunErrorRepository repository) {
        return new DeleteChatAiRunErrorUseCase(repository);
    }
}