package com.mindconnect.infrastructure.treatment.treatmentplan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.treatment.treatmentplan.usecase.DeleteTreatmentPlanUseCase;
import com.mindconnect.application.treatment.treatmentplan.usecase.GetTreatmentPlanByIdUseCase;
import com.mindconnect.application.treatment.treatmentplan.usecase.ListTreatmentPlanUseCase;
import com.mindconnect.application.treatment.treatmentplan.usecase.RegisterTreatmentPlanUseCase;
import com.mindconnect.application.treatment.treatmentplan.usecase.UpdateTreatmentPlanUseCase;
import com.mindconnect.domain.treatment.treatmentplan.port.repository.TreatmentPlanRepository;
import com.mindconnect.infrastructure.treatment.treatmentplan.adapters.out.persistence.repositories.TreatmentPlanJpaRepository;
import com.mindconnect.infrastructure.treatment.treatmentplan.adapters.out.persistence.repositories.TreatmentPlanRepositoryAdapter;

@Configuration
public class TreatmentPlanBeansConfig {

    @Bean
    public TreatmentPlanRepository treatmentPlanRepository(TreatmentPlanJpaRepository jpaRepository) {
        return new TreatmentPlanRepositoryAdapter(jpaRepository);
    }

    @Bean
    public RegisterTreatmentPlanUseCase registerTreatmentPlanUseCase(TreatmentPlanRepository repository) {
        return new RegisterTreatmentPlanUseCase(repository);
    }

    @Bean
    public GetTreatmentPlanByIdUseCase getTreatmentPlanByIdUseCase(TreatmentPlanRepository repository) {
        return new GetTreatmentPlanByIdUseCase(repository);
    }

    @Bean
    public ListTreatmentPlanUseCase listTreatmentPlanUseCase(TreatmentPlanRepository repository) {
        return new ListTreatmentPlanUseCase(repository);
    }

    @Bean
    public UpdateTreatmentPlanUseCase updateTreatmentPlanUseCase(TreatmentPlanRepository repository) {
        return new UpdateTreatmentPlanUseCase(repository);
    }

    @Bean
    public DeleteTreatmentPlanUseCase deleteTreatmentPlanUseCase(TreatmentPlanRepository repository) {
        return new DeleteTreatmentPlanUseCase(repository);
    }
}