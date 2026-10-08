package com.mindconnect.infrastructure.chat.chatconversationstatus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.chat.chatconversationstatus.usecase.DeleteChatConversationStatusUseCase;
import com.mindconnect.application.chat.chatconversationstatus.usecase.GetChatConversationStatusByIdUseCase;
import com.mindconnect.application.chat.chatconversationstatus.usecase.ListChatConversationStatusUseCase;
import com.mindconnect.application.chat.chatconversationstatus.usecase.RegisterChatConversationStatusUseCase;
import com.mindconnect.application.chat.chatconversationstatus.usecase.UpdateChatConversationStatusUseCase;
import com.mindconnect.domain.chat.chatconversationstatus.port.repository.ChatConversationStatusRepository;
import com.mindconnect.infrastructure.chat.chatconversationstatus.adapters.out.persistence.mappers.ChatConversationStatusPersistenceMapper;
import com.mindconnect.infrastructure.chat.chatconversationstatus.adapters.out.persistence.repositories.ChatConversationStatusJpaRepository;
import com.mindconnect.infrastructure.chat.chatconversationstatus.adapters.out.persistence.repositories.ChatConversationStatusRepositoryAdapter;

@Configuration
public class ChatConversationStatusBeansConfig {

    @Bean
    public ChatConversationStatusPersistenceMapper chatConversationStatusPersistenceMapper() {
        return new ChatConversationStatusPersistenceMapper();
    }

    @Bean
    public ChatConversationStatusRepository chatConversationStatusRepository(
            ChatConversationStatusJpaRepository jpaRepository,
            ChatConversationStatusPersistenceMapper mapper) {
        return new ChatConversationStatusRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterChatConversationStatusUseCase registerChatConversationStatusUseCase(ChatConversationStatusRepository repository) {
        return new RegisterChatConversationStatusUseCase(repository);
    }

    @Bean
    public GetChatConversationStatusByIdUseCase getChatConversationStatusByIdUseCase(ChatConversationStatusRepository repository) {
        return new GetChatConversationStatusByIdUseCase(repository);
    }

    @Bean
    public ListChatConversationStatusUseCase listChatConversationStatusUseCase(ChatConversationStatusRepository repository) {
        return new ListChatConversationStatusUseCase(repository);
    }

    @Bean
    public UpdateChatConversationStatusUseCase updateChatConversationStatusUseCase(ChatConversationStatusRepository repository) {
        return new UpdateChatConversationStatusUseCase(repository);
    }

    @Bean
    public DeleteChatConversationStatusUseCase deleteChatConversationStatusUseCase(ChatConversationStatusRepository repository) {
        return new DeleteChatConversationStatusUseCase(repository);
    }
}