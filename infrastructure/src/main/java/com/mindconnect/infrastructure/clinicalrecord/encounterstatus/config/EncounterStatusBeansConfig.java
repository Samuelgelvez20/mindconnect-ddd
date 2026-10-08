package com.mindconnect.infrastructure.clinicalrecord.encounterstatus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.clinicalrecord.encounterstatus.usecase.DeleteEncounterStatusUseCase;
import com.mindconnect.application.clinicalrecord.encounterstatus.usecase.GetEncounterStatusByIdUseCase;
import com.mindconnect.application.clinicalrecord.encounterstatus.usecase.ListEncounterStatusUseCase;
import com.mindconnect.application.clinicalrecord.encounterstatus.usecase.RegisterEncounterStatusUseCase;
import com.mindconnect.application.clinicalrecord.encounterstatus.usecase.UpdateEncounterStatusUseCase;
import com.mindconnect.domain.clinicalrecord.encounterstatus.port.repository.EncounterStatusRepository;
import com.mindconnect.infrastructure.clinicalrecord.encounterstatus.adapters.out.persistence.mappers.EncounterStatusPersistenceMapper;
import com.mindconnect.infrastructure.clinicalrecord.encounterstatus.adapters.out.persistence.repositories.EncounterStatusJpaRepository;
import com.mindconnect.infrastructure.clinicalrecord.encounterstatus.adapters.out.persistence.repositories.EncounterStatusRepositoryAdapter;

@Configuration
public class EncounterStatusBeansConfig {

    @Bean
    public EncounterStatusPersistenceMapper encounterStatusPersistenceMapper() {
        return new EncounterStatusPersistenceMapper();
    }

    @Bean
    public EncounterStatusRepository encounterStatusRepository(
            EncounterStatusJpaRepository jpaRepository,
            EncounterStatusPersistenceMapper mapper) {
        return new EncounterStatusRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterEncounterStatusUseCase registerEncounterStatusUseCase(EncounterStatusRepository repository) {
        return new RegisterEncounterStatusUseCase(repository);
    }

    @Bean
    public GetEncounterStatusByIdUseCase getEncounterStatusByIdUseCase(EncounterStatusRepository repository) {
        return new GetEncounterStatusByIdUseCase(repository);
    }

    @Bean
    public ListEncounterStatusUseCase listEncounterStatusUseCase(EncounterStatusRepository repository) {
        return new ListEncounterStatusUseCase(repository);
    }

    @Bean
    public UpdateEncounterStatusUseCase updateEncounterStatusUseCase(EncounterStatusRepository repository) {
        return new UpdateEncounterStatusUseCase(repository);
    }

    @Bean
    public DeleteEncounterStatusUseCase deleteEncounterStatusUseCase(EncounterStatusRepository repository) {
        return new DeleteEncounterStatusUseCase(repository);
    }
}