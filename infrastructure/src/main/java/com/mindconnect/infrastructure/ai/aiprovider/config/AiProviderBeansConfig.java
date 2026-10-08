package com.mindconnect.infrastructure.ai.aiprovider.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.ai.aiprovider.usecase.DeleteAiProviderUseCase;
import com.mindconnect.application.ai.aiprovider.usecase.GetAiProviderByIdUseCase;
import com.mindconnect.application.ai.aiprovider.usecase.ListAiProviderUseCase;
import com.mindconnect.application.ai.aiprovider.usecase.RegisterAiProviderUseCase;
import com.mindconnect.application.ai.aiprovider.usecase.UpdateAiProviderUseCase;
import com.mindconnect.domain.ai.aiprovider.port.repository.AiProviderRepository;
import com.mindconnect.infrastructure.ai.aiprovider.adapters.out.persistence.mappers.AiProviderPersistenceMapper;
import com.mindconnect.infrastructure.ai.aiprovider.adapters.out.persistence.repositories.AiProviderJpaRepository;
import com.mindconnect.infrastructure.ai.aiprovider.adapters.out.persistence.repositories.AiProviderRepositoryAdapter;

@Configuration
class AiProviderBeansConfig {

    @Bean
    AiProviderPersistenceMapper aiProviderPersistenceMapper() {
        return new AiProviderPersistenceMapper();
    }

    @Bean
    AiProviderRepository aiProviderRepository(
            AiProviderJpaRepository jpaRepository,
            AiProviderPersistenceMapper mapper) {
        return new AiProviderRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    RegisterAiProviderUseCase registerAiProviderUseCase(AiProviderRepository repository) {
        return new RegisterAiProviderUseCase(repository);
    }

    @Bean
    GetAiProviderByIdUseCase getAiProviderByIdUseCase(AiProviderRepository repository) {
        return new GetAiProviderByIdUseCase(repository);
    }

    @Bean
    ListAiProviderUseCase listAiProviderUseCase(AiProviderRepository repository) {
        return new ListAiProviderUseCase(repository);
    }

    @Bean
    UpdateAiProviderUseCase updateAiProviderUseCase(AiProviderRepository repository) {
        return new UpdateAiProviderUseCase(repository);
    }

    @Bean
    DeleteAiProviderUseCase deleteAiProviderUseCase(AiProviderRepository repository) {
        return new DeleteAiProviderUseCase(repository);
    }
}