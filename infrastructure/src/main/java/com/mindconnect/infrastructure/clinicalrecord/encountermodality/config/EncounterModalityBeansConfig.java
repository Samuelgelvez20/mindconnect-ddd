package com.mindconnect.infrastructure.clinicalrecord.encountermodality.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.clinicalrecord.encountermodality.usecase.DeleteEncounterModalityUseCase;
import com.mindconnect.application.clinicalrecord.encountermodality.usecase.GetEncounterModalityByIdUseCase;
import com.mindconnect.application.clinicalrecord.encountermodality.usecase.ListEncounterModalityUseCase;
import com.mindconnect.application.clinicalrecord.encountermodality.usecase.RegisterEncounterModalityUseCase;
import com.mindconnect.application.clinicalrecord.encountermodality.usecase.UpdateEncounterModalityUseCase;
import com.mindconnect.domain.clinicalrecord.encountermodality.port.repository.EncounterModalityRepository;
import com.mindconnect.infrastructure.clinicalrecord.encountermodality.adapters.out.persistence.mappers.EncounterModalityPersistenceMapper;
import com.mindconnect.infrastructure.clinicalrecord.encountermodality.adapters.out.persistence.repositories.EncounterModalityJpaRepository;
import com.mindconnect.infrastructure.clinicalrecord.encountermodality.adapters.out.persistence.repositories.EncounterModalityRepositoryAdapter;

@Configuration
public class EncounterModalityBeansConfig {

    @Bean
    public EncounterModalityPersistenceMapper encounterModalityPersistenceMapper() {
        return new EncounterModalityPersistenceMapper();
    }

    @Bean
    public EncounterModalityRepository encounterModalityRepository(
            EncounterModalityJpaRepository jpaRepository,
            EncounterModalityPersistenceMapper mapper) {
        return new EncounterModalityRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterEncounterModalityUseCase registerEncounterModalityUseCase(EncounterModalityRepository repository) {
        return new RegisterEncounterModalityUseCase(repository);
    }

    @Bean
    public GetEncounterModalityByIdUseCase getEncounterModalityByIdUseCase(EncounterModalityRepository repository) {
        return new GetEncounterModalityByIdUseCase(repository);
    }

    @Bean
    public ListEncounterModalityUseCase listEncounterModalityUseCase(EncounterModalityRepository repository) {
        return new ListEncounterModalityUseCase(repository);
    }

    @Bean
    public UpdateEncounterModalityUseCase updateEncounterModalityUseCase(EncounterModalityRepository repository) {
        return new UpdateEncounterModalityUseCase(repository);
    }

    @Bean
    public DeleteEncounterModalityUseCase deleteEncounterModalityUseCase(EncounterModalityRepository repository) {
        return new DeleteEncounterModalityUseCase(repository);
    }
}