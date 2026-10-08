package com.mindconnect.infrastructure.ai.aimodel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.ai.aimodel.usecase.DeleteAiModelUseCase;
import com.mindconnect.application.ai.aimodel.usecase.GetAiModelByIdUseCase;
import com.mindconnect.application.ai.aimodel.usecase.ListAiModelUseCase;
import com.mindconnect.application.ai.aimodel.usecase.RegisterAiModelUseCase;
import com.mindconnect.application.ai.aimodel.usecase.UpdateAiModelUseCase;
import com.mindconnect.domain.ai.aimodel.port.repository.AiModelRepository;
import com.mindconnect.infrastructure.ai.aimodel.adapters.out.persistence.mappers.AiModelPersistenceMapper;
import com.mindconnect.infrastructure.ai.aimodel.adapters.out.persistence.repositories.AiModelJpaRepository;
import com.mindconnect.infrastructure.ai.aimodel.adapters.out.persistence.repositories.AiModelRepositoryAdapter;

@Configuration
class AiModelBeansConfig {

    @Bean
    AiModelPersistenceMapper aiModelPersistenceMapper() {
        return new AiModelPersistenceMapper();
    }

    @Bean
    AiModelRepository aiModelRepository(
            AiModelJpaRepository jpaRepository,
            AiModelPersistenceMapper mapper) {
        return new AiModelRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    RegisterAiModelUseCase registerAiModelUseCase(AiModelRepository repository) {
        return new RegisterAiModelUseCase(repository);
    }

    @Bean
    GetAiModelByIdUseCase getAiModelByIdUseCase(AiModelRepository repository) {
        return new GetAiModelByIdUseCase(repository);
    }

    @Bean
    ListAiModelUseCase listAiModelUseCase(AiModelRepository repository) {
        return new ListAiModelUseCase(repository);
    }

    @Bean
    UpdateAiModelUseCase updateAiModelUseCase(AiModelRepository repository) {
        return new UpdateAiModelUseCase(repository);
    }

    @Bean
    DeleteAiModelUseCase deleteAiModelUseCase(AiModelRepository repository) {
        return new DeleteAiModelUseCase(repository);
    }
}