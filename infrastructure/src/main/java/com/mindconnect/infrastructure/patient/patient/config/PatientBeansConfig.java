package com.mindconnect.infrastructure.patient.patient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.patient.patient.usecase.DeletePatientUseCase;
import com.mindconnect.application.patient.patient.usecase.GetPatientByIdUseCase;
import com.mindconnect.application.patient.patient.usecase.ListPatientUseCase;
import com.mindconnect.application.patient.patient.usecase.RegisterPatientUseCase;
import com.mindconnect.application.patient.patient.usecase.UpdatePatientUseCase;
import com.mindconnect.domain.patient.patient.port.repository.PatientRepository;
import com.mindconnect.infrastructure.patient.patient.adapters.out.persistence.mappers.PatientPersistenceMapper;
import com.mindconnect.infrastructure.patient.patient.adapters.out.persistence.repositories.PatientJpaRepository;
import com.mindconnect.infrastructure.patient.patient.adapters.out.persistence.repositories.PatientRepositoryAdapter;

@Configuration
public class PatientBeansConfig {

    @Bean
    public PatientPersistenceMapper patientPersistenceMapper() {
        return new PatientPersistenceMapper();
    }

    @Bean
    public PatientRepository patientRepository(
            PatientJpaRepository jpaRepository,
            PatientPersistenceMapper mapper) {
        return new PatientRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterPatientUseCase registerPatientUseCase(PatientRepository repository) {
        return new RegisterPatientUseCase(repository);
    }

    @Bean
    public GetPatientByIdUseCase getPatientByIdUseCase(PatientRepository repository) {
        return new GetPatientByIdUseCase(repository);
    }

    @Bean
    public ListPatientUseCase listPatientUseCase(PatientRepository repository) {
        return new ListPatientUseCase(repository);
    }

    @Bean
    public UpdatePatientUseCase updatePatientUseCase(PatientRepository repository) {
        return new UpdatePatientUseCase(repository);
    }

    @Bean
    public DeletePatientUseCase deletePatientUseCase(PatientRepository repository) {
        return new DeletePatientUseCase(repository);
    }
}