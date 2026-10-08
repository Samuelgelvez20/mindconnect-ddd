package com.mindconnect.infrastructure.chat.chatconversation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.chat.chatconversation.usecase.DeleteChatConversationUseCase;
import com.mindconnect.application.chat.chatconversation.usecase.GetChatConversationByIdUseCase;
import com.mindconnect.application.chat.chatconversation.usecase.ListChatConversationUseCase;
import com.mindconnect.application.chat.chatconversation.usecase.RegisterChatConversationUseCase;
import com.mindconnect.application.chat.chatconversation.usecase.UpdateChatConversationUseCase;
import com.mindconnect.domain.chat.chatconversation.port.repository.ChatConversationRepository;
import com.mindconnect.infrastructure.chat.chatconversation.adapters.out.persistence.mappers.ChatConversationPersistenceMapper;
import com.mindconnect.infrastructure.chat.chatconversation.adapters.out.persistence.repositories.ChatConversationJpaRepository;
import com.mindconnect.infrastructure.chat.chatconversation.adapters.out.persistence.repositories.ChatConversationRepositoryAdapter;

@Configuration
public class ChatConversationBeansConfig {

    @Bean
    public ChatConversationPersistenceMapper chatConversationPersistenceMapper() {
        return new ChatConversationPersistenceMapper();
    }

    @Bean
    public ChatConversationRepository chatConversationRepository(
            ChatConversationJpaRepository jpaRepository,
            ChatConversationPersistenceMapper mapper) {
        return new ChatConversationRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterChatConversationUseCase registerChatConversationUseCase(ChatConversationRepository repository) {
        return new RegisterChatConversationUseCase(repository);
    }

    @Bean
    public GetChatConversationByIdUseCase getChatConversationByIdUseCase(ChatConversationRepository repository) {
        return new GetChatConversationByIdUseCase(repository);
    }

    @Bean
    public ListChatConversationUseCase listChatConversationUseCase(ChatConversationRepository repository) {
        return new ListChatConversationUseCase(repository);
    }

    @Bean
    public UpdateChatConversationUseCase updateChatConversationUseCase(ChatConversationRepository repository) {
        return new UpdateChatConversationUseCase(repository);
    }

    @Bean
    public DeleteChatConversationUseCase deleteChatConversationUseCase(ChatConversationRepository repository) {
        return new DeleteChatConversationUseCase(repository);
    }
}