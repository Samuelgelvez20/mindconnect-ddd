package com.mindconnect.infrastructure.clinicalrecord.clinicalrecordstatus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.clinicalrecord.clinicalrecordstatus.usecase.DeleteClinicalRecordStatusUseCase;
import com.mindconnect.application.clinicalrecord.clinicalrecordstatus.usecase.GetClinicalRecordStatusByIdUseCase;
import com.mindconnect.application.clinicalrecord.clinicalrecordstatus.usecase.ListClinicalRecordStatusUseCase;
import com.mindconnect.application.clinicalrecord.clinicalrecordstatus.usecase.RegisterClinicalRecordStatusUseCase;
import com.mindconnect.application.clinicalrecord.clinicalrecordstatus.usecase.UpdateClinicalRecordStatusUseCase;
import com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.port.repository.ClinicalRecordStatusRepository;
import com.mindconnect.infrastructure.clinicalrecord.clinicalrecordstatus.adapters.out.persistence.mappers.ClinicalRecordStatusPersistenceMapper;
import com.mindconnect.infrastructure.clinicalrecord.clinicalrecordstatus.adapters.out.persistence.repositories.ClinicalRecordStatusJpaRepository;
import com.mindconnect.infrastructure.clinicalrecord.clinicalrecordstatus.adapters.out.persistence.repositories.ClinicalRecordStatusRepositoryAdapter;

@Configuration
public class ClinicalRecordStatusBeansConfig {

    @Bean
    public ClinicalRecordStatusPersistenceMapper clinicalRecordStatusPersistenceMapper() {
        return new ClinicalRecordStatusPersistenceMapper();
    }

    @Bean
    public ClinicalRecordStatusRepository clinicalRecordStatusRepository(
            ClinicalRecordStatusJpaRepository jpaRepository,
            ClinicalRecordStatusPersistenceMapper mapper) {
        return new ClinicalRecordStatusRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterClinicalRecordStatusUseCase registerClinicalRecordStatusUseCase(ClinicalRecordStatusRepository repository) {
        return new RegisterClinicalRecordStatusUseCase(repository);
    }

    @Bean
    public GetClinicalRecordStatusByIdUseCase getClinicalRecordStatusByIdUseCase(ClinicalRecordStatusRepository repository) {
        return new GetClinicalRecordStatusByIdUseCase(repository);
    }

    @Bean
    public ListClinicalRecordStatusUseCase listClinicalRecordStatusUseCase(ClinicalRecordStatusRepository repository) {
        return new ListClinicalRecordStatusUseCase(repository);
    }

    @Bean
    public UpdateClinicalRecordStatusUseCase updateClinicalRecordStatusUseCase(ClinicalRecordStatusRepository repository) {
        return new UpdateClinicalRecordStatusUseCase(repository);
    }

    @Bean
    public DeleteClinicalRecordStatusUseCase deleteClinicalRecordStatusUseCase(ClinicalRecordStatusRepository repository) {
        return new DeleteClinicalRecordStatusUseCase(repository);
    }
}