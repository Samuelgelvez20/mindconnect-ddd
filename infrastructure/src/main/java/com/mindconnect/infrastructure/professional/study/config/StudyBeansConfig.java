package com.mindconnect.infrastructure.professional.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.professional.study.usecase.DeleteStudyUseCase;
import com.mindconnect.application.professional.study.usecase.GetStudyByIdUseCase;
import com.mindconnect.application.professional.study.usecase.ListStudyUseCase;
import com.mindconnect.application.professional.study.usecase.RegisterStudyUseCase;
import com.mindconnect.application.professional.study.usecase.UpdateStudyUseCase;
import com.mindconnect.domain.professional.study.port.repository.StudyRepository;
import com.mindconnect.infrastructure.professional.study.adapters.out.persistence.mappers.StudyPersistenceMapper;
import com.mindconnect.infrastructure.professional.study.adapters.out.persistence.repositories.StudyJpaRepository;
import com.mindconnect.infrastructure.professional.study.adapters.out.persistence.repositories.StudyRepositoryAdapter;

@Configuration
public class StudyBeansConfig {

    @Bean
    public StudyPersistenceMapper studyPersistenceMapper() {
        return new StudyPersistenceMapper();
    }

    @Bean
    public StudyRepository studyRepository(
            StudyJpaRepository jpaRepository,
            StudyPersistenceMapper mapper) {
        return new StudyRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterStudyUseCase registerStudyUseCase(StudyRepository repository) {
        return new RegisterStudyUseCase(repository);
    }

    @Bean
    public GetStudyByIdUseCase getStudyByIdUseCase(StudyRepository repository) {
        return new GetStudyByIdUseCase(repository);
    }

    @Bean
    public ListStudyUseCase listStudyUseCase(StudyRepository repository) {
        return new ListStudyUseCase(repository);
    }

    @Bean
    public UpdateStudyUseCase updateStudyUseCase(StudyRepository repository) {
        return new UpdateStudyUseCase(repository);
    }

    @Bean
    public DeleteStudyUseCase deleteStudyUseCase(StudyRepository repository) {
        return new DeleteStudyUseCase(repository);
    }
}