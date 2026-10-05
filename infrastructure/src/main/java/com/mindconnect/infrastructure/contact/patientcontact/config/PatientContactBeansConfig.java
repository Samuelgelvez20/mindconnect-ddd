package com.mindconnect.infrastructure.contact.patientcontact.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.contact.patientcontact.usecase.DeletePatientContactUseCase;
import com.mindconnect.application.contact.patientcontact.usecase.GetPatientContactByIdUseCase;
import com.mindconnect.application.contact.patientcontact.usecase.ListPatientContactUseCase;
import com.mindconnect.application.contact.patientcontact.usecase.RegisterPatientContactUseCase;
import com.mindconnect.application.contact.patientcontact.usecase.UpdatePatientContactUseCase;
import com.mindconnect.domain.contact.patientcontact.port.repository.PatientContactRepository;
import com.mindconnect.infrastructure.contact.patientcontact.adapters.out.persistence.mappers.PatientContactPersistenceMapper;
import com.mindconnect.infrastructure.contact.patientcontact.adapters.out.persistence.repositories.PatientContactJpaRepository;
import com.mindconnect.infrastructure.contact.patientcontact.adapters.out.persistence.repositories.PatientContactRepositoryAdapter;

@Configuration
public class PatientContactBeansConfig {

    @Bean
    public PatientContactPersistenceMapper patientContactPersistenceMapper() {
        return new PatientContactPersistenceMapper();
    }

    @Bean
    public PatientContactRepository patientContactRepository(
            PatientContactJpaRepository jpaRepository,
            PatientContactPersistenceMapper mapper) {
        return new PatientContactRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterPatientContactUseCase registerPatientContactUseCase(PatientContactRepository repository) {
        return new RegisterPatientContactUseCase(repository);
    }

    @Bean
    public GetPatientContactByIdUseCase getPatientContactByIdUseCase(PatientContactRepository repository) {
        return new GetPatientContactByIdUseCase(repository);
    }

    @Bean
    public ListPatientContactUseCase listPatientContactUseCase(PatientContactRepository repository) {
        return new ListPatientContactUseCase(repository);
    }

    @Bean
    public UpdatePatientContactUseCase updatePatientContactUseCase(PatientContactRepository repository) {
        return new UpdatePatientContactUseCase(repository);
    }

    @Bean
    public DeletePatientContactUseCase deletePatientContactUseCase(PatientContactRepository repository) {
        return new DeletePatientContactUseCase(repository);
    }
}