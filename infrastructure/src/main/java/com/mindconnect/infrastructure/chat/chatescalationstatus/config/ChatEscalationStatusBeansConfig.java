package com.mindconnect.infrastructure.chat.chatescalationstatus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.chat.chatescalationstatus.usecase.DeleteChatEscalationStatusUseCase;
import com.mindconnect.application.chat.chatescalationstatus.usecase.GetChatEscalationStatusByIdUseCase;
import com.mindconnect.application.chat.chatescalationstatus.usecase.ListChatEscalationStatusUseCase;
import com.mindconnect.application.chat.chatescalationstatus.usecase.RegisterChatEscalationStatusUseCase;
import com.mindconnect.application.chat.chatescalationstatus.usecase.UpdateChatEscalationStatusUseCase;
import com.mindconnect.domain.chat.chatescalationstatus.port.repository.ChatEscalationStatusRepository;
import com.mindconnect.infrastructure.chat.chatescalationstatus.adapters.out.persistence.mappers.ChatEscalationStatusPersistenceMapper;
import com.mindconnect.infrastructure.chat.chatescalationstatus.adapters.out.persistence.repositories.ChatEscalationStatusJpaRepository;
import com.mindconnect.infrastructure.chat.chatescalationstatus.adapters.out.persistence.repositories.ChatEscalationStatusRepositoryAdapter;

@Configuration
public class ChatEscalationStatusBeansConfig {

    @Bean
    public ChatEscalationStatusPersistenceMapper chatEscalationStatusPersistenceMapper() {
        return new ChatEscalationStatusPersistenceMapper();
    }

    @Bean
    public ChatEscalationStatusRepository chatEscalationStatusRepository(
            ChatEscalationStatusJpaRepository jpaRepository,
            ChatEscalationStatusPersistenceMapper mapper) {
        return new ChatEscalationStatusRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterChatEscalationStatusUseCase registerChatEscalationStatusUseCase(ChatEscalationStatusRepository repository) {
        return new RegisterChatEscalationStatusUseCase(repository);
    }

    @Bean
    public GetChatEscalationStatusByIdUseCase getChatEscalationStatusByIdUseCase(ChatEscalationStatusRepository repository) {
        return new GetChatEscalationStatusByIdUseCase(repository);
    }

    @Bean
    public ListChatEscalationStatusUseCase listChatEscalationStatusUseCase(ChatEscalationStatusRepository repository) {
        return new ListChatEscalationStatusUseCase(repository);
    }

    @Bean
    public UpdateChatEscalationStatusUseCase updateChatEscalationStatusUseCase(ChatEscalationStatusRepository repository) {
        return new UpdateChatEscalationStatusUseCase(repository);
    }

    @Bean
    public DeleteChatEscalationStatusUseCase deleteChatEscalationStatusUseCase(ChatEscalationStatusRepository repository) {
        return new DeleteChatEscalationStatusUseCase(repository);
    }
}