package com.mindconnect.infrastructure.clinicalcatalog.assessmenttype.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.clinicalcatalog.assessmenttype.usecase.DeleteAssessmentTypeUseCase;
import com.mindconnect.application.clinicalcatalog.assessmenttype.usecase.GetAssessmentTypeByIdUseCase;
import com.mindconnect.application.clinicalcatalog.assessmenttype.usecase.ListAssessmentTypeUseCase;
import com.mindconnect.application.clinicalcatalog.assessmenttype.usecase.RegisterAssessmentTypeUseCase;
import com.mindconnect.application.clinicalcatalog.assessmenttype.usecase.UpdateAssessmentTypeUseCase;
import com.mindconnect.domain.clinicalcatalog.assessmenttype.port.repository.AssessmentTypeRepository;
import com.mindconnect.infrastructure.clinicalcatalog.assessmenttype.adapters.out.persistence.mappers.AssessmentTypePersistenceMapper;
import com.mindconnect.infrastructure.clinicalcatalog.assessmenttype.adapters.out.persistence.repositories.AssessmentTypeJpaRepository;
import com.mindconnect.infrastructure.clinicalcatalog.assessmenttype.adapters.out.persistence.repositories.AssessmentTypeRepositoryAdapter;

@Configuration
public class AssessmentTypeBeansConfig {

    @Bean
    public AssessmentTypePersistenceMapper assessmentTypePersistenceMapper() {
        return new AssessmentTypePersistenceMapper();
    }

    @Bean
    public AssessmentTypeRepository assessmentTypeRepository(
            AssessmentTypeJpaRepository jpaRepository,
            AssessmentTypePersistenceMapper mapper) {
        return new AssessmentTypeRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterAssessmentTypeUseCase registerAssessmentTypeUseCase(AssessmentTypeRepository repository) {
        return new RegisterAssessmentTypeUseCase(repository);
    }

    @Bean
    public GetAssessmentTypeByIdUseCase getAssessmentTypeByIdUseCase(AssessmentTypeRepository repository) {
        return new GetAssessmentTypeByIdUseCase(repository);
    }

    @Bean
    public ListAssessmentTypeUseCase listAssessmentTypeUseCase(AssessmentTypeRepository repository) {
        return new ListAssessmentTypeUseCase(repository);
    }

    @Bean
    public UpdateAssessmentTypeUseCase updateAssessmentTypeUseCase(AssessmentTypeRepository repository) {
        return new UpdateAssessmentTypeUseCase(repository);
    }

    @Bean
    public DeleteAssessmentTypeUseCase deleteAssessmentTypeUseCase(AssessmentTypeRepository repository) {
        return new DeleteAssessmentTypeUseCase(repository);
    }
}