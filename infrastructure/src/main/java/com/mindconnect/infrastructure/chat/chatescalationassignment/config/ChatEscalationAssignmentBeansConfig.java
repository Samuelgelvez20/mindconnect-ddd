package com.mindconnect.infrastructure.chat.chatescalationassignment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.chat.chatescalationassignment.usecase.DeleteChatEscalationAssignmentUseCase;
import com.mindconnect.application.chat.chatescalationassignment.usecase.GetChatEscalationAssignmentByIdUseCase;
import com.mindconnect.application.chat.chatescalationassignment.usecase.ListChatEscalationAssignmentUseCase;
import com.mindconnect.application.chat.chatescalationassignment.usecase.RegisterChatEscalationAssignmentUseCase;
import com.mindconnect.application.chat.chatescalationassignment.usecase.UpdateChatEscalationAssignmentUseCase;
import com.mindconnect.domain.chat.chatescalationassignment.port.repository.ChatEscalationAssignmentRepository;
import com.mindconnect.infrastructure.chat.chatescalationassignment.adapters.out.persistence.mappers.ChatEscalationAssignmentPersistenceMapper;
import com.mindconnect.infrastructure.chat.chatescalationassignment.adapters.out.persistence.repositories.ChatEscalationAssignmentJpaRepository;
import com.mindconnect.infrastructure.chat.chatescalationassignment.adapters.out.persistence.repositories.ChatEscalationAssignmentRepositoryAdapter;

@Configuration
public class ChatEscalationAssignmentBeansConfig {

    @Bean
    public ChatEscalationAssignmentPersistenceMapper chatEscalationAssignmentPersistenceMapper() {
        return new ChatEscalationAssignmentPersistenceMapper();
    }

    @Bean
    public ChatEscalationAssignmentRepository chatEscalationAssignmentRepository(
            ChatEscalationAssignmentJpaRepository jpaRepository,
            ChatEscalationAssignmentPersistenceMapper mapper) {
        return new ChatEscalationAssignmentRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterChatEscalationAssignmentUseCase registerChatEscalationAssignmentUseCase(ChatEscalationAssignmentRepository repository) {
        return new RegisterChatEscalationAssignmentUseCase(repository);
    }

    @Bean
    public GetChatEscalationAssignmentByIdUseCase getChatEscalationAssignmentByIdUseCase(ChatEscalationAssignmentRepository repository) {
        return new GetChatEscalationAssignmentByIdUseCase(repository);
    }

    @Bean
    public ListChatEscalationAssignmentUseCase listChatEscalationAssignmentUseCase(ChatEscalationAssignmentRepository repository) {
        return new ListChatEscalationAssignmentUseCase(repository);
    }

    @Bean
    public UpdateChatEscalationAssignmentUseCase updateChatEscalationAssignmentUseCase(ChatEscalationAssignmentRepository repository) {
        return new UpdateChatEscalationAssignmentUseCase(repository);
    }

    @Bean
    public DeleteChatEscalationAssignmentUseCase deleteChatEscalationAssignmentUseCase(ChatEscalationAssignmentRepository repository) {
        return new DeleteChatEscalationAssignmentUseCase(repository);
    }
}