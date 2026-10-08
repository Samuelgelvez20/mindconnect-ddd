package com.mindconnect.infrastructure.clinicalrecord.riskassessment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.clinicalrecord.riskassessment.usecase.DeleteRiskAssessmentUseCase;
import com.mindconnect.application.clinicalrecord.riskassessment.usecase.GetRiskAssessmentByIdUseCase;
import com.mindconnect.application.clinicalrecord.riskassessment.usecase.ListRiskAssessmentUseCase;
import com.mindconnect.application.clinicalrecord.riskassessment.usecase.RegisterRiskAssessmentUseCase;
import com.mindconnect.application.clinicalrecord.riskassessment.usecase.UpdateRiskAssessmentUseCase;
import com.mindconnect.domain.clinicalrecord.riskassessment.port.repository.RiskAssessmentRepository;
import com.mindconnect.infrastructure.clinicalrecord.riskassessment.adapters.out.persistence.mappers.RiskAssessmentPersistenceMapper;
import com.mindconnect.infrastructure.clinicalrecord.riskassessment.adapters.out.persistence.repositories.RiskAssessmentJpaRepository;
import com.mindconnect.infrastructure.clinicalrecord.riskassessment.adapters.out.persistence.repositories.RiskAssessmentRepositoryAdapter;

@Configuration
public class RiskAssessmentBeansConfig {

    @Bean
    public RiskAssessmentPersistenceMapper riskAssessmentPersistenceMapper() {
        return new RiskAssessmentPersistenceMapper();
    }

    @Bean
    public RiskAssessmentRepository riskAssessmentRepository(
            RiskAssessmentJpaRepository jpaRepository,
            RiskAssessmentPersistenceMapper mapper) {
        return new RiskAssessmentRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterRiskAssessmentUseCase registerRiskAssessmentUseCase(RiskAssessmentRepository repository) {
        return new RegisterRiskAssessmentUseCase(repository);
    }

    @Bean
    public GetRiskAssessmentByIdUseCase getRiskAssessmentByIdUseCase(RiskAssessmentRepository repository) {
        return new GetRiskAssessmentByIdUseCase(repository);
    }

    @Bean
    public ListRiskAssessmentUseCase listRiskAssessmentUseCase(RiskAssessmentRepository repository) {
        return new ListRiskAssessmentUseCase(repository);
    }

    @Bean
    public UpdateRiskAssessmentUseCase updateRiskAssessmentUseCase(RiskAssessmentRepository repository) {
        return new UpdateRiskAssessmentUseCase(repository);
    }

    @Bean
    public DeleteRiskAssessmentUseCase deleteRiskAssessmentUseCase(RiskAssessmentRepository repository) {
        return new DeleteRiskAssessmentUseCase(repository);
    }
}