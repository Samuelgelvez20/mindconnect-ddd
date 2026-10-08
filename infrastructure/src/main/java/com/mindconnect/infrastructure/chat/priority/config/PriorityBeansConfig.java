package com.mindconnect.infrastructure.chat.priority.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.chat.priority.usecase.DeletePriorityUseCase;
import com.mindconnect.application.chat.priority.usecase.GetPriorityByIdUseCase;
import com.mindconnect.application.chat.priority.usecase.ListPriorityUseCase;
import com.mindconnect.application.chat.priority.usecase.RegisterPriorityUseCase;
import com.mindconnect.application.chat.priority.usecase.UpdatePriorityUseCase;
import com.mindconnect.domain.chat.priority.port.repository.PriorityRepository;
import com.mindconnect.infrastructure.chat.priority.adapters.out.persistence.mappers.PriorityPersistenceMapper;
import com.mindconnect.infrastructure.chat.priority.adapters.out.persistence.repositories.PriorityJpaRepository;
import com.mindconnect.infrastructure.chat.priority.adapters.out.persistence.repositories.PriorityRepositoryAdapter;

@Configuration
public class PriorityBeansConfig {

    @Bean
    public PriorityPersistenceMapper priorityPersistenceMapper() {
        return new PriorityPersistenceMapper();
    }

    @Bean
    public PriorityRepository priorityRepository(
            PriorityJpaRepository jpaRepository,
            PriorityPersistenceMapper mapper) {
        return new PriorityRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterPriorityUseCase registerPriorityUseCase(PriorityRepository repository) {
        return new RegisterPriorityUseCase(repository);
    }

    @Bean
    public GetPriorityByIdUseCase getPriorityByIdUseCase(PriorityRepository repository) {
        return new GetPriorityByIdUseCase(repository);
    }

    @Bean
    public ListPriorityUseCase listPriorityUseCase(PriorityRepository repository) {
        return new ListPriorityUseCase(repository);
    }

    @Bean
    public UpdatePriorityUseCase updatePriorityUseCase(PriorityRepository repository) {
        return new UpdatePriorityUseCase(repository);
    }

    @Bean
    public DeletePriorityUseCase deletePriorityUseCase(PriorityRepository repository) {
        return new DeletePriorityUseCase(repository);
    }
}