package com.mindconnect.infrastructure.ai.chatairunstatus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.ai.chatairunstatus.usecase.DeleteChatAiRunStatusUseCase;
import com.mindconnect.application.ai.chatairunstatus.usecase.GetChatAiRunStatusByIdUseCase;
import com.mindconnect.application.ai.chatairunstatus.usecase.ListChatAiRunStatusUseCase;
import com.mindconnect.application.ai.chatairunstatus.usecase.RegisterChatAiRunStatusUseCase;
import com.mindconnect.application.ai.chatairunstatus.usecase.UpdateChatAiRunStatusUseCase;
import com.mindconnect.domain.ai.chatairunstatus.port.repository.ChatAiRunStatusRepository;
import com.mindconnect.infrastructure.ai.chatairunstatus.adapters.out.persistence.mappers.ChatAiRunStatusPersistenceMapper;
import com.mindconnect.infrastructure.ai.chatairunstatus.adapters.out.persistence.repositories.ChatAiRunStatusJpaRepository;
import com.mindconnect.infrastructure.ai.chatairunstatus.adapters.out.persistence.repositories.ChatAiRunStatusRepositoryAdapter;

@Configuration
class ChatAiRunStatusBeansConfig {

    @Bean
    ChatAiRunStatusPersistenceMapper chatAiRunStatusPersistenceMapper() {
        return new ChatAiRunStatusPersistenceMapper();
    }

    @Bean
    ChatAiRunStatusRepository chatAiRunStatusRepository(
            ChatAiRunStatusJpaRepository jpaRepository,
            ChatAiRunStatusPersistenceMapper mapper) {
        return new ChatAiRunStatusRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    RegisterChatAiRunStatusUseCase registerChatAiRunStatusUseCase(ChatAiRunStatusRepository repository) {
        return new RegisterChatAiRunStatusUseCase(repository);
    }

    @Bean
    GetChatAiRunStatusByIdUseCase getChatAiRunStatusByIdUseCase(ChatAiRunStatusRepository repository) {
        return new GetChatAiRunStatusByIdUseCase(repository);
    }

    @Bean
    ListChatAiRunStatusUseCase listChatAiRunStatusUseCase(ChatAiRunStatusRepository repository) {
        return new ListChatAiRunStatusUseCase(repository);
    }

    @Bean
    UpdateChatAiRunStatusUseCase updateChatAiRunStatusUseCase(ChatAiRunStatusRepository repository) {
        return new UpdateChatAiRunStatusUseCase(repository);
    }

    @Bean
    DeleteChatAiRunStatusUseCase deleteChatAiRunStatusUseCase(ChatAiRunStatusRepository repository) {
        return new DeleteChatAiRunStatusUseCase(repository);
    }
}