package com.mindconnect.infrastructure.clinicalcatalog.diagnosticsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.clinicalcatalog.diagnosticsystem.usecase.DeleteDiagnosticSystemUseCase;
import com.mindconnect.application.clinicalcatalog.diagnosticsystem.usecase.GetDiagnosticSystemByIdUseCase;
import com.mindconnect.application.clinicalcatalog.diagnosticsystem.usecase.ListDiagnosticSystemUseCase;
import com.mindconnect.application.clinicalcatalog.diagnosticsystem.usecase.RegisterDiagnosticSystemUseCase;
import com.mindconnect.application.clinicalcatalog.diagnosticsystem.usecase.UpdateDiagnosticSystemUseCase;
import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.port.repository.DiagnosticSystemRepository;
import com.mindconnect.infrastructure.clinicalcatalog.diagnosticsystem.adapters.out.persistence.mappers.DiagnosticSystemPersistenceMapper;
import com.mindconnect.infrastructure.clinicalcatalog.diagnosticsystem.adapters.out.persistence.repositories.DiagnosticSystemJpaRepository;
import com.mindconnect.infrastructure.clinicalcatalog.diagnosticsystem.adapters.out.persistence.repositories.DiagnosticSystemRepositoryAdapter;

@Configuration
public class DiagnosticSystemBeansConfig {

    @Bean
    public DiagnosticSystemPersistenceMapper diagnosticSystemPersistenceMapper() {
        return new DiagnosticSystemPersistenceMapper();
    }

    @Bean
    public DiagnosticSystemRepository diagnosticSystemRepository(
            DiagnosticSystemJpaRepository jpaRepository,
            DiagnosticSystemPersistenceMapper mapper) {
        return new DiagnosticSystemRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public com.mindconnect.application.clinicalcatalog.diagnosticsystem.usecase.RegisterDiagnosticSystemUseCase registerDiagnosticSystemUseCase(DiagnosticSystemRepository repository) {
        return new com.mindconnect.application.clinicalcatalog.diagnosticsystem.usecase.RegisterDiagnosticSystemUseCase(repository);
    }

    @Bean
    public com.mindconnect.application.clinicalcatalog.diagnosticsystem.usecase.GetDiagnosticSystemByIdUseCase getDiagnosticSystemByIdUseCase(DiagnosticSystemRepository repository) {
        return new com.mindconnect.application.clinicalcatalog.diagnosticsystem.usecase.GetDiagnosticSystemByIdUseCase(repository);
    }

    @Bean
    public com.mindconnect.application.clinicalcatalog.diagnosticsystem.usecase.ListDiagnosticSystemUseCase listDiagnosticSystemUseCase(DiagnosticSystemRepository repository) {
        return new com.mindconnect.application.clinicalcatalog.diagnosticsystem.usecase.ListDiagnosticSystemUseCase(repository);
    }

    @Bean
    public com.mindconnect.application.clinicalcatalog.diagnosticsystem.usecase.UpdateDiagnosticSystemUseCase updateDiagnosticSystemUseCase(DiagnosticSystemRepository repository) {
        return new com.mindconnect.application.clinicalcatalog.diagnosticsystem.usecase.UpdateDiagnosticSystemUseCase(repository);
    }

    @Bean
    public com.mindconnect.application.clinicalcatalog.diagnosticsystem.usecase.DeleteDiagnosticSystemUseCase deleteDiagnosticSystemUseCase(DiagnosticSystemRepository repository) {
        return new com.mindconnect.application.clinicalcatalog.diagnosticsystem.usecase.DeleteDiagnosticSystemUseCase(repository);
    }
}