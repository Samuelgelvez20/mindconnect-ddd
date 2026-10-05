package com.mindconnect.infrastructure.treatment.treatmentgoalstatus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.treatment.treatmentgoalstatus.usecase.DeleteTreatmentGoalStatusUseCase;
import com.mindconnect.application.treatment.treatmentgoalstatus.usecase.GetTreatmentGoalStatusByIdUseCase;
import com.mindconnect.application.treatment.treatmentgoalstatus.usecase.ListTreatmentGoalStatusUseCase;
import com.mindconnect.application.treatment.treatmentgoalstatus.usecase.RegisterTreatmentGoalStatusUseCase;
import com.mindconnect.application.treatment.treatmentgoalstatus.usecase.UpdateTreatmentGoalStatusUseCase;
import com.mindconnect.domain.treatment.treatmentgoalstatus.port.repository.TreatmentGoalStatusRepository;
import com.mindconnect.infrastructure.treatment.treatmentgoalstatus.adapters.out.persistence.repositories.TreatmentGoalStatusJpaRepository;
import com.mindconnect.infrastructure.treatment.treatmentgoalstatus.adapters.out.persistence.repositories.TreatmentGoalStatusRepositoryAdapter;

@Configuration
public class TreatmentGoalStatusBeansConfig {

    @Bean
    public TreatmentGoalStatusRepository treatmentGoalStatusRepository(TreatmentGoalStatusJpaRepository jpaRepository) {
        return new TreatmentGoalStatusRepositoryAdapter(jpaRepository);
    }

    @Bean
    public RegisterTreatmentGoalStatusUseCase registerTreatmentGoalStatusUseCase(TreatmentGoalStatusRepository repository) {
        return new RegisterTreatmentGoalStatusUseCase(repository);
    }

    @Bean
    public GetTreatmentGoalStatusByIdUseCase getTreatmentGoalStatusByIdUseCase(TreatmentGoalStatusRepository repository) {
        return new GetTreatmentGoalStatusByIdUseCase(repository);
    }

    @Bean
    public ListTreatmentGoalStatusUseCase listTreatmentGoalStatusUseCase(TreatmentGoalStatusRepository repository) {
        return new ListTreatmentGoalStatusUseCase(repository);
    }

    @Bean
    public UpdateTreatmentGoalStatusUseCase updateTreatmentGoalStatusUseCase(TreatmentGoalStatusRepository repository) {
        return new UpdateTreatmentGoalStatusUseCase(repository);
    }

    @Bean
    public DeleteTreatmentGoalStatusUseCase deleteTreatmentGoalStatusUseCase(TreatmentGoalStatusRepository repository) {
        return new DeleteTreatmentGoalStatusUseCase(repository);
    }
}