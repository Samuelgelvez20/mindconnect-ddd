package com.mindconnect.infrastructure.professional.professionaltype.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.professional.professionaltype.usecase.DeleteProfessionalTypeUseCase;
import com.mindconnect.application.professional.professionaltype.usecase.GetProfessionalTypeByIdUseCase;
import com.mindconnect.application.professional.professionaltype.usecase.ListProfessionalTypeUseCase;
import com.mindconnect.application.professional.professionaltype.usecase.RegisterProfessionalTypeUseCase;
import com.mindconnect.application.professional.professionaltype.usecase.UpdateProfessionalTypeUseCase;
import com.mindconnect.domain.professional.professionaltype.port.repository.ProfessionalTypeRepository;
import com.mindconnect.infrastructure.professional.professionaltype.adapters.out.persistence.mappers.ProfessionalTypePersistenceMapper;
import com.mindconnect.infrastructure.professional.professionaltype.adapters.out.persistence.repositories.ProfessionalTypeJpaRepository;
import com.mindconnect.infrastructure.professional.professionaltype.adapters.out.persistence.repositories.ProfessionalTypeRepositoryAdapter;

@Configuration
public class ProfessionalTypeBeansConfig {

    @Bean
    public ProfessionalTypePersistenceMapper professionalTypePersistenceMapper() {
        return new ProfessionalTypePersistenceMapper();
    }

    @Bean
    public ProfessionalTypeRepository professionalTypeRepository(
            ProfessionalTypeJpaRepository jpaRepository,
            ProfessionalTypePersistenceMapper mapper) {
        return new ProfessionalTypeRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterProfessionalTypeUseCase registerProfessionalTypeUseCase(ProfessionalTypeRepository repository) {
        return new RegisterProfessionalTypeUseCase(repository);
    }

    @Bean
    public GetProfessionalTypeByIdUseCase getProfessionalTypeByIdUseCase(ProfessionalTypeRepository repository) {
        return new GetProfessionalTypeByIdUseCase(repository);
    }

    @Bean
    public ListProfessionalTypeUseCase listProfessionalTypeUseCase(ProfessionalTypeRepository repository) {
        return new ListProfessionalTypeUseCase(repository);
    }

    @Bean
    public UpdateProfessionalTypeUseCase updateProfessionalTypeUseCase(ProfessionalTypeRepository repository) {
        return new UpdateProfessionalTypeUseCase(repository);
    }

    @Bean
    public DeleteProfessionalTypeUseCase deleteProfessionalTypeUseCase(ProfessionalTypeRepository repository) {
        return new DeleteProfessionalTypeUseCase(repository);
    }
}