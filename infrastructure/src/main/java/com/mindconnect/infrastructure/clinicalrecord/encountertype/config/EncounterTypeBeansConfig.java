package com.mindconnect.infrastructure.clinicalrecord.encountertype.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.clinicalrecord.encountertype.usecase.DeleteEncounterTypeUseCase;
import com.mindconnect.application.clinicalrecord.encountertype.usecase.GetEncounterTypeByIdUseCase;
import com.mindconnect.application.clinicalrecord.encountertype.usecase.ListEncounterTypeUseCase;
import com.mindconnect.application.clinicalrecord.encountertype.usecase.RegisterEncounterTypeUseCase;
import com.mindconnect.application.clinicalrecord.encountertype.usecase.UpdateEncounterTypeUseCase;
import com.mindconnect.domain.clinicalrecord.encountertype.port.repository.EncounterTypeRepository;
import com.mindconnect.infrastructure.clinicalrecord.encountertype.adapters.out.persistence.mappers.EncounterTypePersistenceMapper;
import com.mindconnect.infrastructure.clinicalrecord.encountertype.adapters.out.persistence.repositories.EncounterTypeJpaRepository;
import com.mindconnect.infrastructure.clinicalrecord.encountertype.adapters.out.persistence.repositories.EncounterTypeRepositoryAdapter;

@Configuration
public class EncounterTypeBeansConfig {

    @Bean
    public EncounterTypePersistenceMapper encounterTypePersistenceMapper() {
        return new EncounterTypePersistenceMapper();
    }

    @Bean
    public EncounterTypeRepository encounterTypeRepository(
            EncounterTypeJpaRepository jpaRepository,
            EncounterTypePersistenceMapper mapper) {
        return new EncounterTypeRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterEncounterTypeUseCase registerEncounterTypeUseCase(EncounterTypeRepository repository) {
        return new RegisterEncounterTypeUseCase(repository);
    }

    @Bean
    public GetEncounterTypeByIdUseCase getEncounterTypeByIdUseCase(EncounterTypeRepository repository) {
        return new GetEncounterTypeByIdUseCase(repository);
    }

    @Bean
    public ListEncounterTypeUseCase listEncounterTypeUseCase(EncounterTypeRepository repository) {
        return new ListEncounterTypeUseCase(repository);
    }

    @Bean
    public UpdateEncounterTypeUseCase updateEncounterTypeUseCase(EncounterTypeRepository repository) {
        return new UpdateEncounterTypeUseCase(repository);
    }

    @Bean
    public DeleteEncounterTypeUseCase deleteEncounterTypeUseCase(EncounterTypeRepository repository) {
        return new DeleteEncounterTypeUseCase(repository);
    }
}