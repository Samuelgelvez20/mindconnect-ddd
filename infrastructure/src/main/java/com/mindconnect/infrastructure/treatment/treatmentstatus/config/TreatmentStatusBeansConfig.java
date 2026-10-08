package com.mindconnect.infrastructure.treatment.treatmentstatus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.treatment.treatmentstatus.usecase.DeleteTreatmentStatusUseCase;
import com.mindconnect.application.treatment.treatmentstatus.usecase.GetTreatmentStatusByIdUseCase;
import com.mindconnect.application.treatment.treatmentstatus.usecase.ListTreatmentStatusUseCase;
import com.mindconnect.application.treatment.treatmentstatus.usecase.RegisterTreatmentStatusUseCase;
import com.mindconnect.application.treatment.treatmentstatus.usecase.UpdateTreatmentStatusUseCase;
import com.mindconnect.domain.treatment.treatmentstatus.port.repository.TreatmentStatusRepository;
import com.mindconnect.infrastructure.treatment.treatmentstatus.adapters.out.persistence.mappers.TreatmentStatusPersistenceMapper;
import com.mindconnect.infrastructure.treatment.treatmentstatus.adapters.out.persistence.repositories.TreatmentStatusJpaRepository;
import com.mindconnect.infrastructure.treatment.treatmentstatus.adapters.out.persistence.repositories.TreatmentStatusRepositoryAdapter;

@Configuration
public class TreatmentStatusBeansConfig {

    @Bean
    public TreatmentStatusPersistenceMapper treatmentStatusPersistenceMapper() {
        return new TreatmentStatusPersistenceMapper();
    }

    @Bean
    public TreatmentStatusRepository treatmentStatusRepository(
            TreatmentStatusJpaRepository jpaRepository,
            TreatmentStatusPersistenceMapper mapper) {
        return new TreatmentStatusRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterTreatmentStatusUseCase registerTreatmentStatusUseCase(TreatmentStatusRepository repository) {
        return new RegisterTreatmentStatusUseCase(repository);
    }

    @Bean
    public GetTreatmentStatusByIdUseCase getTreatmentStatusByIdUseCase(TreatmentStatusRepository repository) {
        return new GetTreatmentStatusByIdUseCase(repository);
    }

    @Bean
    public ListTreatmentStatusUseCase listTreatmentStatusUseCase(TreatmentStatusRepository repository) {
        return new ListTreatmentStatusUseCase(repository);
    }

    @Bean
    public UpdateTreatmentStatusUseCase updateTreatmentStatusUseCase(TreatmentStatusRepository repository) {
        return new UpdateTreatmentStatusUseCase(repository);
    }

    @Bean
    public DeleteTreatmentStatusUseCase deleteTreatmentStatusUseCase(TreatmentStatusRepository repository) {
        return new DeleteTreatmentStatusUseCase(repository);
    }
}