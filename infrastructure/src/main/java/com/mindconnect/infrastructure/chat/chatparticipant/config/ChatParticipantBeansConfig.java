package com.mindconnect.infrastructure.chat.chatparticipant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.chat.chatparticipant.usecase.DeleteChatParticipantUseCase;
import com.mindconnect.application.chat.chatparticipant.usecase.GetChatParticipantByIdUseCase;
import com.mindconnect.application.chat.chatparticipant.usecase.ListChatParticipantUseCase;
import com.mindconnect.application.chat.chatparticipant.usecase.RegisterChatParticipantUseCase;
import com.mindconnect.application.chat.chatparticipant.usecase.UpdateChatParticipantUseCase;
import com.mindconnect.domain.chat.chatparticipant.port.repository.ChatParticipantRepository;
import com.mindconnect.infrastructure.chat.chatparticipant.adapters.out.persistence.mappers.ChatParticipantPersistenceMapper;
import com.mindconnect.infrastructure.chat.chatparticipant.adapters.out.persistence.repositories.ChatParticipantJpaRepository;
import com.mindconnect.infrastructure.chat.chatparticipant.adapters.out.persistence.repositories.ChatParticipantRepositoryAdapter;

@Configuration
public class ChatParticipantBeansConfig {

    @Bean
    public ChatParticipantPersistenceMapper chatParticipantPersistenceMapper() {
        return new ChatParticipantPersistenceMapper();
    }

    @Bean
    public ChatParticipantRepository chatParticipantRepository(
            ChatParticipantJpaRepository jpaRepository,
            ChatParticipantPersistenceMapper mapper) {
        return new ChatParticipantRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterChatParticipantUseCase registerChatParticipantUseCase(ChatParticipantRepository repository) {
        return new RegisterChatParticipantUseCase(repository);
    }

    @Bean
    public GetChatParticipantByIdUseCase getChatParticipantByIdUseCase(ChatParticipantRepository repository) {
        return new GetChatParticipantByIdUseCase(repository);
    }

    @Bean
    public ListChatParticipantUseCase listChatParticipantUseCase(ChatParticipantRepository repository) {
        return new ListChatParticipantUseCase(repository);
    }

    @Bean
    public UpdateChatParticipantUseCase updateChatParticipantUseCase(ChatParticipantRepository repository) {
        return new UpdateChatParticipantUseCase(repository);
    }

    @Bean
    public DeleteChatParticipantUseCase deleteChatParticipantUseCase(ChatParticipantRepository repository) {
        return new DeleteChatParticipantUseCase(repository);
    }
}