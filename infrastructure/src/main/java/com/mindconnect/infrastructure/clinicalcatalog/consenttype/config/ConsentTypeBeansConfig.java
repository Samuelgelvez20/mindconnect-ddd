package com.mindconnect.infrastructure.clinicalcatalog.consenttype.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.clinicalcatalog.consenttype.usecase.DeleteConsentTypeUseCase;
import com.mindconnect.application.clinicalcatalog.consenttype.usecase.GetConsentTypeByIdUseCase;
import com.mindconnect.application.clinicalcatalog.consenttype.usecase.ListConsentTypeUseCase;
import com.mindconnect.application.clinicalcatalog.consenttype.usecase.RegisterConsentTypeUseCase;
import com.mindconnect.application.clinicalcatalog.consenttype.usecase.UpdateConsentTypeUseCase;
import com.mindconnect.domain.clinicalcatalog.consenttype.port.repository.ConsentTypeRepository;
import com.mindconnect.infrastructure.clinicalcatalog.consenttype.adapters.out.persistence.mappers.ConsentTypePersistenceMapper;
import com.mindconnect.infrastructure.clinicalcatalog.consenttype.adapters.out.persistence.repositories.ConsentTypeJpaRepository;
import com.mindconnect.infrastructure.clinicalcatalog.consenttype.adapters.out.persistence.repositories.ConsentTypeRepositoryAdapter;

@Configuration
public class ConsentTypeBeansConfig {

    @Bean
    public ConsentTypePersistenceMapper consentTypePersistenceMapper() {
        return new ConsentTypePersistenceMapper();
    }

    @Bean
    public ConsentTypeRepository consentTypeRepository(
            ConsentTypeJpaRepository jpaRepository,
            ConsentTypePersistenceMapper mapper) {
        return new ConsentTypeRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterConsentTypeUseCase registerConsentTypeUseCase(ConsentTypeRepository repository) {
        return new RegisterConsentTypeUseCase(repository);
    }

    @Bean
    public GetConsentTypeByIdUseCase getConsentTypeByIdUseCase(ConsentTypeRepository repository) {
        return new GetConsentTypeByIdUseCase(repository);
    }

    @Bean
    public ListConsentTypeUseCase listConsentTypeUseCase(ConsentTypeRepository repository) {
        return new ListConsentTypeUseCase(repository);
    }

    @Bean
    public UpdateConsentTypeUseCase updateConsentTypeUseCase(ConsentTypeRepository repository) {
        return new UpdateConsentTypeUseCase(repository);
    }

    @Bean
    public DeleteConsentTypeUseCase deleteConsentTypeUseCase(ConsentTypeRepository repository) {
        return new DeleteConsentTypeUseCase(repository);
    }
}