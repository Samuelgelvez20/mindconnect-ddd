package com.mindconnect.infrastructure.chat.chatescalation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.chat.chatescalation.usecase.DeleteChatEscalationUseCase;
import com.mindconnect.application.chat.chatescalation.usecase.GetChatEscalationByIdUseCase;
import com.mindconnect.application.chat.chatescalation.usecase.ListChatEscalationUseCase;
import com.mindconnect.application.chat.chatescalation.usecase.RegisterChatEscalationUseCase;
import com.mindconnect.application.chat.chatescalation.usecase.UpdateChatEscalationUseCase;
import com.mindconnect.domain.chat.chatescalation.port.repository.ChatEscalationRepository;
import com.mindconnect.infrastructure.chat.chatescalation.adapters.out.persistence.mappers.ChatEscalationPersistenceMapper;
import com.mindconnect.infrastructure.chat.chatescalation.adapters.out.persistence.repositories.ChatEscalationJpaRepository;
import com.mindconnect.infrastructure.chat.chatescalation.adapters.out.persistence.repositories.ChatEscalationRepositoryAdapter;

@Configuration
public class ChatEscalationBeansConfig {

    @Bean
    public ChatEscalationPersistenceMapper chatEscalationPersistenceMapper() {
        return new ChatEscalationPersistenceMapper();
    }

    @Bean
    public ChatEscalationRepository chatEscalationRepository(
            ChatEscalationJpaRepository jpaRepository,
            ChatEscalationPersistenceMapper mapper) {
        return new ChatEscalationRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterChatEscalationUseCase registerChatEscalationUseCase(ChatEscalationRepository repository) {
        return new RegisterChatEscalationUseCase(repository);
    }

    @Bean
    public GetChatEscalationByIdUseCase getChatEscalationByIdUseCase(ChatEscalationRepository repository) {
        return new GetChatEscalationByIdUseCase(repository);
    }

    @Bean
    public ListChatEscalationUseCase listChatEscalationUseCase(ChatEscalationRepository repository) {
        return new ListChatEscalationUseCase(repository);
    }

    @Bean
    public UpdateChatEscalationUseCase updateChatEscalationUseCase(ChatEscalationRepository repository) {
        return new UpdateChatEscalationUseCase(repository);
    }

    @Bean
    public DeleteChatEscalationUseCase deleteChatEscalationUseCase(ChatEscalationRepository repository) {
        return new DeleteChatEscalationUseCase(repository);
    }
}