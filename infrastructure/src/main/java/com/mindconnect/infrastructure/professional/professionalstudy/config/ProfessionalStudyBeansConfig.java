package com.mindconnect.infrastructure.professional.professionalstudy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.professional.professionalstudy.usecase.DeleteProfessionalStudyUseCase;
import com.mindconnect.application.professional.professionalstudy.usecase.GetProfessionalStudyByIdUseCase;
import com.mindconnect.application.professional.professionalstudy.usecase.ListProfessionalStudyUseCase;
import com.mindconnect.application.professional.professionalstudy.usecase.RegisterProfessionalStudyUseCase;
import com.mindconnect.application.professional.professionalstudy.usecase.UpdateProfessionalStudyUseCase;
import com.mindconnect.domain.professional.professionalstudy.port.repository.ProfessionalStudyRepository;
import com.mindconnect.infrastructure.professional.professionalstudy.adapters.out.persistence.mappers.ProfessionalStudyPersistenceMapper;
import com.mindconnect.infrastructure.professional.professionalstudy.adapters.out.persistence.repositories.ProfessionalStudyJpaRepository;
import com.mindconnect.infrastructure.professional.professionalstudy.adapters.out.persistence.repositories.ProfessionalStudyRepositoryAdapter;

@Configuration
public class ProfessionalStudyBeansConfig {

    @Bean
    public ProfessionalStudyPersistenceMapper professionalStudyPersistenceMapper() {
        return new ProfessionalStudyPersistenceMapper();
    }

    @Bean
    public ProfessionalStudyRepository professionalStudyRepository(
            ProfessionalStudyJpaRepository jpaRepository,
            ProfessionalStudyPersistenceMapper mapper) {
        return new ProfessionalStudyRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterProfessionalStudyUseCase registerProfessionalStudyUseCase(ProfessionalStudyRepository repository) {
        return new RegisterProfessionalStudyUseCase(repository);
    }

    @Bean
    public GetProfessionalStudyByIdUseCase getProfessionalStudyByIdUseCase(ProfessionalStudyRepository repository) {
        return new GetProfessionalStudyByIdUseCase(repository);
    }

    @Bean
    public ListProfessionalStudyUseCase listProfessionalStudyUseCase(ProfessionalStudyRepository repository) {
        return new ListProfessionalStudyUseCase(repository);
    }

    @Bean
    public UpdateProfessionalStudyUseCase updateProfessionalStudyUseCase(ProfessionalStudyRepository repository) {
        return new UpdateProfessionalStudyUseCase(repository);
    }

    @Bean
    public DeleteProfessionalStudyUseCase deleteProfessionalStudyUseCase(ProfessionalStudyRepository repository) {
        return new DeleteProfessionalStudyUseCase(repository);
    }
}