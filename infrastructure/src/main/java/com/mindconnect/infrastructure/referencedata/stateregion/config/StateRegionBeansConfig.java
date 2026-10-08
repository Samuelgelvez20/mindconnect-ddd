package com.mindconnect.infrastructure.referencedata.stateregion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.referencedata.stateregion.usecase.DeleteStateRegionUseCase;
import com.mindconnect.application.referencedata.stateregion.usecase.GetStateRegionByIdUseCase;
import com.mindconnect.application.referencedata.stateregion.usecase.ListStateRegionUseCase;
import com.mindconnect.application.referencedata.stateregion.usecase.RegisterStateRegionUseCase;
import com.mindconnect.application.referencedata.stateregion.usecase.UpdateStateRegionUseCase;
import com.mindconnect.domain.referencedata.stateregion.port.repository.StateRegionRepository;
import com.mindconnect.infrastructure.referencedata.stateregion.adapters.out.persistence.mappers.StateRegionPersistenceMapper;
import com.mindconnect.infrastructure.referencedata.stateregion.adapters.out.persistence.repositories.StateRegionJpaRepository;
import com.mindconnect.infrastructure.referencedata.stateregion.adapters.out.persistence.repositories.StateRegionRepositoryAdapter;

@Configuration
public class StateRegionBeansConfig {

    @Bean
    public StateRegionPersistenceMapper stateRegionPersistenceMapper() {
        return new StateRegionPersistenceMapper();
    }

    @Bean
    public StateRegionRepository stateRegionRepository(
            StateRegionJpaRepository jpaRepository,
            StateRegionPersistenceMapper mapper) {
        return new StateRegionRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterStateRegionUseCase registerStateRegionUseCase(StateRegionRepository repository) {
        return new RegisterStateRegionUseCase(repository);
    }

    @Bean
    public GetStateRegionByIdUseCase getStateRegionByIdUseCase(StateRegionRepository repository) {
        return new GetStateRegionByIdUseCase(repository);
    }

    @Bean
    public ListStateRegionUseCase listStateRegionUseCase(StateRegionRepository repository) {
        return new ListStateRegionUseCase(repository);
    }

    @Bean
    public UpdateStateRegionUseCase updateStateRegionUseCase(StateRegionRepository repository) {
        return new UpdateStateRegionUseCase(repository);
    }

    @Bean
    public DeleteStateRegionUseCase deleteStateRegionUseCase(StateRegionRepository repository) {
        return new DeleteStateRegionUseCase(repository);
    }
}