package com.mindconnect.infrastructure.professional.professional.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.professional.professional.usecase.DeleteProfessionalUseCase;
import com.mindconnect.application.professional.professional.usecase.GetProfessionalByIdUseCase;
import com.mindconnect.application.professional.professional.usecase.ListProfessionalUseCase;
import com.mindconnect.application.professional.professional.usecase.RegisterProfessionalUseCase;
import com.mindconnect.application.professional.professional.usecase.UpdateProfessionalUseCase;
import com.mindconnect.domain.professional.professional.port.repository.ProfessionalRepository;
import com.mindconnect.infrastructure.professional.professional.adapters.out.persistence.mappers.ProfessionalPersistenceMapper;
import com.mindconnect.infrastructure.professional.professional.adapters.out.persistence.repositories.ProfessionalJpaRepository;
import com.mindconnect.infrastructure.professional.professional.adapters.out.persistence.repositories.ProfessionalRepositoryAdapter;

@Configuration
public class ProfessionalBeansConfig {

    @Bean
    public ProfessionalPersistenceMapper professionalPersistenceMapper() {
        return new ProfessionalPersistenceMapper();
    }

    @Bean
    public ProfessionalRepository professionalRepository(
            ProfessionalJpaRepository jpaRepository,
            ProfessionalPersistenceMapper mapper) {
        return new ProfessionalRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterProfessionalUseCase registerProfessionalUseCase(ProfessionalRepository repository) {
        return new RegisterProfessionalUseCase(repository);
    }

    @Bean
    public GetProfessionalByIdUseCase getProfessionalByIdUseCase(ProfessionalRepository repository) {
        return new GetProfessionalByIdUseCase(repository);
    }

    @Bean
    public ListProfessionalUseCase listProfessionalUseCase(ProfessionalRepository repository) {
        return new ListProfessionalUseCase(repository);
    }

    @Bean
    public UpdateProfessionalUseCase updateProfessionalUseCase(ProfessionalRepository repository) {
        return new UpdateProfessionalUseCase(repository);
    }

    @Bean
    public DeleteProfessionalUseCase deleteProfessionalUseCase(ProfessionalRepository repository) {
        return new DeleteProfessionalUseCase(repository);
    }
}