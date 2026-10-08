package com.mindconnect.infrastructure.clinicalrecord.mentalstatusexam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.clinicalrecord.mentalstatusexam.usecase.DeleteMentalStatusExamUseCase;
import com.mindconnect.application.clinicalrecord.mentalstatusexam.usecase.GetMentalStatusExamByIdUseCase;
import com.mindconnect.application.clinicalrecord.mentalstatusexam.usecase.ListMentalStatusExamUseCase;
import com.mindconnect.application.clinicalrecord.mentalstatusexam.usecase.RegisterMentalStatusExamUseCase;
import com.mindconnect.application.clinicalrecord.mentalstatusexam.usecase.UpdateMentalStatusExamUseCase;
import com.mindconnect.domain.clinicalrecord.mentalstatusexam.port.repository.MentalStatusExamRepository;
import com.mindconnect.infrastructure.clinicalrecord.mentalstatusexam.adapters.out.persistence.mappers.MentalStatusExamPersistenceMapper;
import com.mindconnect.infrastructure.clinicalrecord.mentalstatusexam.adapters.out.persistence.repositories.MentalStatusExamJpaRepository;
import com.mindconnect.infrastructure.clinicalrecord.mentalstatusexam.adapters.out.persistence.repositories.MentalStatusExamRepositoryAdapter;

@Configuration
public class MentalStatusExamBeansConfig {

    @Bean
    public MentalStatusExamPersistenceMapper mentalStatusExamPersistenceMapper() {
        return new MentalStatusExamPersistenceMapper();
    }

    @Bean
    public MentalStatusExamRepository mentalStatusExamRepository(
            MentalStatusExamJpaRepository jpaRepository,
            MentalStatusExamPersistenceMapper mapper) {
        return new MentalStatusExamRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterMentalStatusExamUseCase registerMentalStatusExamUseCase(MentalStatusExamRepository repository) {
        return new RegisterMentalStatusExamUseCase(repository);
    }

    @Bean
    public GetMentalStatusExamByIdUseCase getMentalStatusExamByIdUseCase(MentalStatusExamRepository repository) {
        return new GetMentalStatusExamByIdUseCase(repository);
    }

    @Bean
    public ListMentalStatusExamUseCase listMentalStatusExamUseCase(MentalStatusExamRepository repository) {
        return new ListMentalStatusExamUseCase(repository);
    }

    @Bean
    public UpdateMentalStatusExamUseCase updateMentalStatusExamUseCase(MentalStatusExamRepository repository) {
        return new UpdateMentalStatusExamUseCase(repository);
    }

    @Bean
    public DeleteMentalStatusExamUseCase deleteMentalStatusExamUseCase(MentalStatusExamRepository repository) {
        return new DeleteMentalStatusExamUseCase(repository);
    }
}