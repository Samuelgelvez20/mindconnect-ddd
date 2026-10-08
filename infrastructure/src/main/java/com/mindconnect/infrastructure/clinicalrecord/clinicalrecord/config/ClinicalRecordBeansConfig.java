package com.mindconnect.infrastructure.clinicalrecord.clinicalrecord.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.clinicalrecord.clinicalrecord.usecase.DeleteClinicalRecordUseCase;
import com.mindconnect.application.clinicalrecord.clinicalrecord.usecase.GetClinicalRecordByIdUseCase;
import com.mindconnect.application.clinicalrecord.clinicalrecord.usecase.ListClinicalRecordUseCase;
import com.mindconnect.application.clinicalrecord.clinicalrecord.usecase.RegisterClinicalRecordUseCase;
import com.mindconnect.application.clinicalrecord.clinicalrecord.usecase.UpdateClinicalRecordUseCase;
import com.mindconnect.domain.clinicalrecord.clinicalrecord.port.repository.ClinicalRecordRepository;
import com.mindconnect.infrastructure.clinicalrecord.clinicalrecord.adapters.out.persistence.mappers.ClinicalRecordPersistenceMapper;
import com.mindconnect.infrastructure.clinicalrecord.clinicalrecord.adapters.out.persistence.repositories.ClinicalRecordJpaRepository;
import com.mindconnect.infrastructure.clinicalrecord.clinicalrecord.adapters.out.persistence.repositories.ClinicalRecordRepositoryAdapter;

@Configuration
public class ClinicalRecordBeansConfig {

    @Bean
    public ClinicalRecordPersistenceMapper clinicalRecordPersistenceMapper() {
        return new ClinicalRecordPersistenceMapper();
    }

    @Bean
    public ClinicalRecordRepository clinicalRecordRepository(
            ClinicalRecordJpaRepository jpaRepository,
            ClinicalRecordPersistenceMapper mapper) {
        return new ClinicalRecordRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterClinicalRecordUseCase registerClinicalRecordUseCase(ClinicalRecordRepository repository) {
        return new RegisterClinicalRecordUseCase(repository);
    }

    @Bean
    public GetClinicalRecordByIdUseCase getClinicalRecordByIdUseCase(ClinicalRecordRepository repository) {
        return new GetClinicalRecordByIdUseCase(repository);
    }

    @Bean
    public ListClinicalRecordUseCase listClinicalRecordUseCase(ClinicalRecordRepository repository) {
        return new ListClinicalRecordUseCase(repository);
    }

    @Bean
    public UpdateClinicalRecordUseCase updateClinicalRecordUseCase(ClinicalRecordRepository repository) {
        return new UpdateClinicalRecordUseCase(repository);
    }

    @Bean
    public DeleteClinicalRecordUseCase deleteClinicalRecordUseCase(ClinicalRecordRepository repository) {
        return new DeleteClinicalRecordUseCase(repository);
    }
}