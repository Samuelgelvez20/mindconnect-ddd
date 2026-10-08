package com.mindconnect.infrastructure.chat.chatmessage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.chat.chatmessage.usecase.DeleteChatMessageUseCase;
import com.mindconnect.application.chat.chatmessage.usecase.GetChatMessageByIdUseCase;
import com.mindconnect.application.chat.chatmessage.usecase.ListChatMessageUseCase;
import com.mindconnect.application.chat.chatmessage.usecase.RegisterChatMessageUseCase;
import com.mindconnect.application.chat.chatmessage.usecase.UpdateChatMessageUseCase;
import com.mindconnect.domain.chat.chatmessage.port.repository.ChatMessageRepository;
import com.mindconnect.infrastructure.chat.chatmessage.adapters.out.persistence.mappers.ChatMessagePersistenceMapper;
import com.mindconnect.infrastructure.chat.chatmessage.adapters.out.persistence.repositories.ChatMessageJpaRepository;
import com.mindconnect.infrastructure.chat.chatmessage.adapters.out.persistence.repositories.ChatMessageRepositoryAdapter;

@Configuration
public class ChatMessageBeansConfig {

    @Bean
    public ChatMessagePersistenceMapper chatMessagePersistenceMapper() {
        return new ChatMessagePersistenceMapper();
    }

    @Bean
    public ChatMessageRepository chatMessageRepository(
            ChatMessageJpaRepository jpaRepository,
            ChatMessagePersistenceMapper mapper) {
        return new ChatMessageRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterChatMessageUseCase registerChatMessageUseCase(ChatMessageRepository repository) {
        return new RegisterChatMessageUseCase(repository);
    }

    @Bean
    public GetChatMessageByIdUseCase getChatMessageByIdUseCase(ChatMessageRepository repository) {
        return new GetChatMessageByIdUseCase(repository);
    }

    @Bean
    public ListChatMessageUseCase listChatMessageUseCase(ChatMessageRepository repository) {
        return new ListChatMessageUseCase(repository);
    }

    @Bean
    public UpdateChatMessageUseCase updateChatMessageUseCase(ChatMessageRepository repository) {
        return new UpdateChatMessageUseCase(repository);
    }

    @Bean
    public DeleteChatMessageUseCase deleteChatMessageUseCase(ChatMessageRepository repository) {
        return new DeleteChatMessageUseCase(repository);
    }
}