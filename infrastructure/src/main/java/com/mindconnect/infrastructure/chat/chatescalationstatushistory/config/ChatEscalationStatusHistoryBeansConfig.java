package com.mindconnect.infrastructure.chat.chatescalationstatushistory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.chat.chatescalationstatushistory.usecase.DeleteChatEscalationStatusHistoryUseCase;
import com.mindconnect.application.chat.chatescalationstatushistory.usecase.GetChatEscalationStatusHistoryByIdUseCase;
import com.mindconnect.application.chat.chatescalationstatushistory.usecase.ListChatEscalationStatusHistoryUseCase;
import com.mindconnect.application.chat.chatescalationstatushistory.usecase.RegisterChatEscalationStatusHistoryUseCase;
import com.mindconnect.application.chat.chatescalationstatushistory.usecase.UpdateChatEscalationStatusHistoryUseCase;
import com.mindconnect.domain.chat.chatescalationstatushistory.port.repository.ChatEscalationStatusHistoryRepository;
import com.mindconnect.infrastructure.chat.chatescalationstatushistory.adapters.out.persistence.mappers.ChatEscalationStatusHistoryPersistenceMapper;
import com.mindconnect.infrastructure.chat.chatescalationstatushistory.adapters.out.persistence.repositories.ChatEscalationStatusHistoryJpaRepository;
import com.mindconnect.infrastructure.chat.chatescalationstatushistory.adapters.out.persistence.repositories.ChatEscalationStatusHistoryRepositoryAdapter;

@Configuration
public class ChatEscalationStatusHistoryBeansConfig {

    @Bean
    public ChatEscalationStatusHistoryPersistenceMapper chatEscalationStatusHistoryPersistenceMapper() {
        return new ChatEscalationStatusHistoryPersistenceMapper();
    }

    @Bean
    public ChatEscalationStatusHistoryRepository chatEscalationStatusHistoryRepository(
            ChatEscalationStatusHistoryJpaRepository jpaRepository,
            ChatEscalationStatusHistoryPersistenceMapper mapper) {
        return new ChatEscalationStatusHistoryRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterChatEscalationStatusHistoryUseCase registerChatEscalationStatusHistoryUseCase(ChatEscalationStatusHistoryRepository repository) {
        return new RegisterChatEscalationStatusHistoryUseCase(repository);
    }

    @Bean
    public GetChatEscalationStatusHistoryByIdUseCase getChatEscalationStatusHistoryByIdUseCase(ChatEscalationStatusHistoryRepository repository) {
        return new GetChatEscalationStatusHistoryByIdUseCase(repository);
    }

    @Bean
    public ListChatEscalationStatusHistoryUseCase listChatEscalationStatusHistoryUseCase(ChatEscalationStatusHistoryRepository repository) {
        return new ListChatEscalationStatusHistoryUseCase(repository);
    }

    @Bean
    public UpdateChatEscalationStatusHistoryUseCase updateChatEscalationStatusHistoryUseCase(ChatEscalationStatusHistoryRepository repository) {
        return new UpdateChatEscalationStatusHistoryUseCase(repository);
    }

    @Bean
    public DeleteChatEscalationStatusHistoryUseCase deleteChatEscalationStatusHistoryUseCase(ChatEscalationStatusHistoryRepository repository) {
        return new DeleteChatEscalationStatusHistoryUseCase(repository);
    }
}