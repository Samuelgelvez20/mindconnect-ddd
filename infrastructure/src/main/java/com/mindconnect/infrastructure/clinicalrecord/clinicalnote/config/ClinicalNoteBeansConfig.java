package com.mindconnect.infrastructure.clinicalrecord.clinicalnote.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.clinicalrecord.clinicalnote.usecase.DeleteClinicalNoteUseCase;
import com.mindconnect.application.clinicalrecord.clinicalnote.usecase.GetClinicalNoteByIdUseCase;
import com.mindconnect.application.clinicalrecord.clinicalnote.usecase.ListClinicalNoteUseCase;
import com.mindconnect.application.clinicalrecord.clinicalnote.usecase.RegisterClinicalNoteUseCase;
import com.mindconnect.application.clinicalrecord.clinicalnote.usecase.UpdateClinicalNoteUseCase;
import com.mindconnect.domain.clinicalrecord.clinicalnote.port.repository.ClinicalNoteRepository;
import com.mindconnect.infrastructure.clinicalrecord.clinicalnote.adapters.out.persistence.mappers.ClinicalNotePersistenceMapper;
import com.mindconnect.infrastructure.clinicalrecord.clinicalnote.adapters.out.persistence.repositories.ClinicalNoteJpaRepository;
import com.mindconnect.infrastructure.clinicalrecord.clinicalnote.adapters.out.persistence.repositories.ClinicalNoteRepositoryAdapter;

@Configuration
public class ClinicalNoteBeansConfig {

    @Bean
    public ClinicalNotePersistenceMapper clinicalNotePersistenceMapper() {
        return new ClinicalNotePersistenceMapper();
    }

    @Bean
    public ClinicalNoteRepository clinicalNoteRepository(
            ClinicalNoteJpaRepository jpaRepository,
            ClinicalNotePersistenceMapper mapper) {
        return new ClinicalNoteRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterClinicalNoteUseCase registerClinicalNoteUseCase(ClinicalNoteRepository repository) {
        return new RegisterClinicalNoteUseCase(repository);
    }

    @Bean
    public GetClinicalNoteByIdUseCase getClinicalNoteByIdUseCase(ClinicalNoteRepository repository) {
        return new GetClinicalNoteByIdUseCase(repository);
    }

    @Bean
    public ListClinicalNoteUseCase listClinicalNoteUseCase(ClinicalNoteRepository repository) {
        return new ListClinicalNoteUseCase(repository);
    }

    @Bean
    public UpdateClinicalNoteUseCase updateClinicalNoteUseCase(ClinicalNoteRepository repository) {
        return new UpdateClinicalNoteUseCase(repository);
    }

    @Bean
    public DeleteClinicalNoteUseCase deleteClinicalNoteUseCase(ClinicalNoteRepository repository) {
        return new DeleteClinicalNoteUseCase(repository);
    }
}