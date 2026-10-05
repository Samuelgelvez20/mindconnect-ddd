package com.mindconnect.infrastructure.patient.patientallergy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.patient.patientallergy.usecase.DeletePatientAllergyUseCase;
import com.mindconnect.application.patient.patientallergy.usecase.GetPatientAllergyByIdUseCase;
import com.mindconnect.application.patient.patientallergy.usecase.ListPatientAllergyUseCase;
import com.mindconnect.application.patient.patientallergy.usecase.RegisterPatientAllergyUseCase;
import com.mindconnect.application.patient.patientallergy.usecase.UpdatePatientAllergyUseCase;
import com.mindconnect.domain.patient.patientallergy.port.repository.PatientAllergyRepository;
import com.mindconnect.infrastructure.patient.patientallergy.adapters.out.persistence.mappers.PatientAllergyPersistenceMapper;
import com.mindconnect.infrastructure.patient.patientallergy.adapters.out.persistence.repositories.PatientAllergyJpaRepository;
import com.mindconnect.infrastructure.patient.patientallergy.adapters.out.persistence.repositories.PatientAllergyRepositoryAdapter;

@Configuration
public class PatientAllergyBeansConfig {

    @Bean
    public PatientAllergyPersistenceMapper patientAllergyPersistenceMapper() {
        return new PatientAllergyPersistenceMapper();
    }

    @Bean
    public PatientAllergyRepository patientAllergyRepository(
            PatientAllergyJpaRepository jpaRepository,
            PatientAllergyPersistenceMapper mapper) {
        return new PatientAllergyRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterPatientAllergyUseCase registerPatientAllergyUseCase(PatientAllergyRepository repository) {
        return new RegisterPatientAllergyUseCase(repository);
    }

    @Bean
    public GetPatientAllergyByIdUseCase getPatientAllergyByIdUseCase(PatientAllergyRepository repository) {
        return new GetPatientAllergyByIdUseCase(repository);
    }

    @Bean
    public ListPatientAllergyUseCase listPatientAllergyUseCase(PatientAllergyRepository repository) {
        return new ListPatientAllergyUseCase(repository);
    }

    @Bean
    public UpdatePatientAllergyUseCase updatePatientAllergyUseCase(PatientAllergyRepository repository) {
        return new UpdatePatientAllergyUseCase(repository);
    }

    @Bean
    public DeletePatientAllergyUseCase deletePatientAllergyUseCase(PatientAllergyRepository repository) {
        return new DeletePatientAllergyUseCase(repository);
    }
}