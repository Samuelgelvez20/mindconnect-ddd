package com.mindconnect.infrastructure.chat.messagetype.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.chat.messagetype.usecase.DeleteMessageTypeUseCase;
import com.mindconnect.application.chat.messagetype.usecase.GetMessageTypeByIdUseCase;
import com.mindconnect.application.chat.messagetype.usecase.ListMessageTypeUseCase;
import com.mindconnect.application.chat.messagetype.usecase.RegisterMessageTypeUseCase;
import com.mindconnect.application.chat.messagetype.usecase.UpdateMessageTypeUseCase;
import com.mindconnect.domain.chat.messagetype.port.repository.MessageTypeRepository;
import com.mindconnect.infrastructure.chat.messagetype.adapters.out.persistence.mappers.MessageTypePersistenceMapper;
import com.mindconnect.infrastructure.chat.messagetype.adapters.out.persistence.repositories.MessageTypeJpaRepository;
import com.mindconnect.infrastructure.chat.messagetype.adapters.out.persistence.repositories.MessageTypeRepositoryAdapter;

@Configuration
public class MessageTypeBeansConfig {

    @Bean
    public MessageTypePersistenceMapper messageTypePersistenceMapper() {
        return new MessageTypePersistenceMapper();
    }

    @Bean
    public MessageTypeRepository messageTypeRepository(
            MessageTypeJpaRepository jpaRepository,
            MessageTypePersistenceMapper mapper) {
        return new MessageTypeRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterMessageTypeUseCase registerMessageTypeUseCase(MessageTypeRepository repository) {
        return new RegisterMessageTypeUseCase(repository);
    }

    @Bean
    public GetMessageTypeByIdUseCase getMessageTypeByIdUseCase(MessageTypeRepository repository) {
        return new GetMessageTypeByIdUseCase(repository);
    }

    @Bean
    public ListMessageTypeUseCase listMessageTypeUseCase(MessageTypeRepository repository) {
        return new ListMessageTypeUseCase(repository);
    }

    @Bean
    public UpdateMessageTypeUseCase updateMessageTypeUseCase(MessageTypeRepository repository) {
        return new UpdateMessageTypeUseCase(repository);
    }

    @Bean
    public DeleteMessageTypeUseCase deleteMessageTypeUseCase(MessageTypeRepository repository) {
        return new DeleteMessageTypeUseCase(repository);
    }
}