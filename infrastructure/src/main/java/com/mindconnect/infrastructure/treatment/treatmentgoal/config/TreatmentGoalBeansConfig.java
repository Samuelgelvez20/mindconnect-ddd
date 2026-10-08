package com.mindconnect.infrastructure.treatment.treatmentgoal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.treatment.treatmentgoal.usecase.DeleteTreatmentGoalUseCase;
import com.mindconnect.application.treatment.treatmentgoal.usecase.GetTreatmentGoalByIdUseCase;
import com.mindconnect.application.treatment.treatmentgoal.usecase.ListTreatmentGoalUseCase;
import com.mindconnect.application.treatment.treatmentgoal.usecase.RegisterTreatmentGoalUseCase;
import com.mindconnect.application.treatment.treatmentgoal.usecase.UpdateTreatmentGoalUseCase;
import com.mindconnect.domain.treatment.treatmentgoal.port.repository.TreatmentGoalRepository;
import com.mindconnect.infrastructure.treatment.treatmentgoal.adapters.out.persistence.mappers.TreatmentGoalPersistenceMapper;
import com.mindconnect.infrastructure.treatment.treatmentgoal.adapters.out.persistence.repositories.TreatmentGoalJpaRepository;
import com.mindconnect.infrastructure.treatment.treatmentgoal.adapters.out.persistence.repositories.TreatmentGoalRepositoryAdapter;

@Configuration
public class TreatmentGoalBeansConfig {

    @Bean
    public TreatmentGoalPersistenceMapper treatmentGoalPersistenceMapper() {
        return new TreatmentGoalPersistenceMapper();
    }

    @Bean
    public TreatmentGoalRepository treatmentGoalRepository(
            TreatmentGoalJpaRepository jpaRepository,
            TreatmentGoalPersistenceMapper mapper) {
        return new TreatmentGoalRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterTreatmentGoalUseCase registerTreatmentGoalUseCase(TreatmentGoalRepository repository) {
        return new RegisterTreatmentGoalUseCase(repository);
    }

    @Bean
    public GetTreatmentGoalByIdUseCase getTreatmentGoalByIdUseCase(TreatmentGoalRepository repository) {
        return new GetTreatmentGoalByIdUseCase(repository);
    }

    @Bean
    public ListTreatmentGoalUseCase listTreatmentGoalUseCase(TreatmentGoalRepository repository) {
        return new ListTreatmentGoalUseCase(repository);
    }

    @Bean
    public UpdateTreatmentGoalUseCase updateTreatmentGoalUseCase(TreatmentGoalRepository repository) {
        return new UpdateTreatmentGoalUseCase(repository);
    }

    @Bean
    public DeleteTreatmentGoalUseCase deleteTreatmentGoalUseCase(TreatmentGoalRepository repository) {
        return new DeleteTreatmentGoalUseCase(repository);
    }
}