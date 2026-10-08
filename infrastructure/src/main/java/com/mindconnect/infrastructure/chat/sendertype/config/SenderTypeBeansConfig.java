package com.mindconnect.infrastructure.chat.sendertype.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.chat.sendertype.usecase.DeleteSenderTypeUseCase;
import com.mindconnect.application.chat.sendertype.usecase.GetSenderTypeByIdUseCase;
import com.mindconnect.application.chat.sendertype.usecase.ListSenderTypeUseCase;
import com.mindconnect.application.chat.sendertype.usecase.RegisterSenderTypeUseCase;
import com.mindconnect.application.chat.sendertype.usecase.UpdateSenderTypeUseCase;
import com.mindconnect.domain.chat.sendertype.port.repository.SenderTypeRepository;
import com.mindconnect.infrastructure.chat.sendertype.adapters.out.persistence.mappers.SenderTypePersistenceMapper;
import com.mindconnect.infrastructure.chat.sendertype.adapters.out.persistence.repositories.SenderTypeJpaRepository;
import com.mindconnect.infrastructure.chat.sendertype.adapters.out.persistence.repositories.SenderTypeRepositoryAdapter;

@Configuration
public class SenderTypeBeansConfig {

    @Bean
    public SenderTypePersistenceMapper senderTypePersistenceMapper() {
        return new SenderTypePersistenceMapper();
    }

    @Bean
    public SenderTypeRepository senderTypeRepository(
            SenderTypeJpaRepository jpaRepository,
            SenderTypePersistenceMapper mapper) {
        return new SenderTypeRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterSenderTypeUseCase registerSenderTypeUseCase(SenderTypeRepository repository) {
        return new RegisterSenderTypeUseCase(repository);
    }

    @Bean
    public GetSenderTypeByIdUseCase getSenderTypeByIdUseCase(SenderTypeRepository repository) {
        return new GetSenderTypeByIdUseCase(repository);
    }

    @Bean
    public ListSenderTypeUseCase listSenderTypeUseCase(SenderTypeRepository repository) {
        return new ListSenderTypeUseCase(repository);
    }

    @Bean
    public UpdateSenderTypeUseCase updateSenderTypeUseCase(SenderTypeRepository repository) {
        return new UpdateSenderTypeUseCase(repository);
    }

    @Bean
    public DeleteSenderTypeUseCase deleteSenderTypeUseCase(SenderTypeRepository repository) {
        return new DeleteSenderTypeUseCase(repository);
    }
}