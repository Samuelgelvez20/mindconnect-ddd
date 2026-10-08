package com.mindconnect.infrastructure.clinicalrecord.encounter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.clinicalrecord.encounter.usecase.DeleteEncounterUseCase;
import com.mindconnect.application.clinicalrecord.encounter.usecase.GetEncounterByIdUseCase;
import com.mindconnect.application.clinicalrecord.encounter.usecase.ListEncounterUseCase;
import com.mindconnect.application.clinicalrecord.encounter.usecase.RegisterEncounterUseCase;
import com.mindconnect.application.clinicalrecord.encounter.usecase.UpdateEncounterUseCase;
import com.mindconnect.domain.clinicalrecord.encounter.port.repository.EncounterRepository;
import com.mindconnect.infrastructure.clinicalrecord.encounter.adapters.out.persistence.mappers.EncounterPersistenceMapper;
import com.mindconnect.infrastructure.clinicalrecord.encounter.adapters.out.persistence.repositories.EncounterJpaRepository;
import com.mindconnect.infrastructure.clinicalrecord.encounter.adapters.out.persistence.repositories.EncounterRepositoryAdapter;

@Configuration
public class EncounterBeansConfig {

    @Bean
    public EncounterPersistenceMapper encounterPersistenceMapper() {
        return new EncounterPersistenceMapper();
    }

    @Bean
    public EncounterRepository encounterRepository(
            EncounterJpaRepository jpaRepository,
            EncounterPersistenceMapper mapper) {
        return new EncounterRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterEncounterUseCase registerEncounterUseCase(EncounterRepository repository) {
        return new RegisterEncounterUseCase(repository);
    }

    @Bean
    public GetEncounterByIdUseCase getEncounterByIdUseCase(EncounterRepository repository) {
        return new GetEncounterByIdUseCase(repository);
    }

    @Bean
    public ListEncounterUseCase listEncounterUseCase(EncounterRepository repository) {
        return new ListEncounterUseCase(repository);
    }

    @Bean
    public UpdateEncounterUseCase updateEncounterUseCase(EncounterRepository repository) {
        return new UpdateEncounterUseCase(repository);
    }

    @Bean
    public DeleteEncounterUseCase deleteEncounterUseCase(EncounterRepository repository) {
        return new DeleteEncounterUseCase(repository);
    }
}