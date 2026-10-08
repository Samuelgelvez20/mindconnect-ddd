package com.mindconnect.infrastructure.clinicalrecord.risklevel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.clinicalrecord.risklevel.usecase.DeleteRiskLevelUseCase;
import com.mindconnect.application.clinicalrecord.risklevel.usecase.GetRiskLevelByIdUseCase;
import com.mindconnect.application.clinicalrecord.risklevel.usecase.ListRiskLevelUseCase;
import com.mindconnect.application.clinicalrecord.risklevel.usecase.RegisterRiskLevelUseCase;
import com.mindconnect.application.clinicalrecord.risklevel.usecase.UpdateRiskLevelUseCase;
import com.mindconnect.domain.clinicalrecord.risklevel.port.repository.RiskLevelRepository;
import com.mindconnect.infrastructure.clinicalrecord.risklevel.adapters.out.persistence.mappers.RiskLevelPersistenceMapper;
import com.mindconnect.infrastructure.clinicalrecord.risklevel.adapters.out.persistence.repositories.RiskLevelJpaRepository;
import com.mindconnect.infrastructure.clinicalrecord.risklevel.adapters.out.persistence.repositories.RiskLevelRepositoryAdapter;

@Configuration
public class RiskLevelBeansConfig {

    @Bean
    public RiskLevelPersistenceMapper riskLevelPersistenceMapper() {
        return new RiskLevelPersistenceMapper();
    }

    @Bean
    public RiskLevelRepository riskLevelRepository(
            RiskLevelJpaRepository jpaRepository,
            RiskLevelPersistenceMapper mapper) {
        return new RiskLevelRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterRiskLevelUseCase registerRiskLevelUseCase(RiskLevelRepository repository) {
        return new RegisterRiskLevelUseCase(repository);
    }

    @Bean
    public GetRiskLevelByIdUseCase getRiskLevelByIdUseCase(RiskLevelRepository repository) {
        return new GetRiskLevelByIdUseCase(repository);
    }

    @Bean
    public ListRiskLevelUseCase listRiskLevelUseCase(RiskLevelRepository repository) {
        return new ListRiskLevelUseCase(repository);
    }

    @Bean
    public UpdateRiskLevelUseCase updateRiskLevelUseCase(RiskLevelRepository repository) {
        return new UpdateRiskLevelUseCase(repository);
    }

    @Bean
    public DeleteRiskLevelUseCase deleteRiskLevelUseCase(RiskLevelRepository repository) {
        return new DeleteRiskLevelUseCase(repository);
    }
}