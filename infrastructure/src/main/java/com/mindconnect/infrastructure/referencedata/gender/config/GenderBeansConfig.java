package com.mindconnect.infrastructure.referencedata.gender.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.referencedata.gender.usecase.DeleteGenderUseCase;
import com.mindconnect.application.referencedata.gender.usecase.GetGenderByIdUseCase;
import com.mindconnect.application.referencedata.gender.usecase.ListGenderUseCase;
import com.mindconnect.application.referencedata.gender.usecase.RegisterGenderUseCase;
import com.mindconnect.application.referencedata.gender.usecase.UpdateGenderUseCase;
import com.mindconnect.domain.referencedata.gender.port.repository.GenderRepository;
import com.mindconnect.infrastructure.referencedata.gender.adapters.out.persistence.mappers.GenderPersistenceMapper;
import com.mindconnect.infrastructure.referencedata.gender.adapters.out.persistence.repositories.GenderJpaRepository;
import com.mindconnect.infrastructure.referencedata.gender.adapters.out.persistence.repositories.GenderRepositoryAdapter;

@Configuration
public class GenderBeansConfig {

    @Bean
    public GenderPersistenceMapper genderPersistenceMapper() {
        return new GenderPersistenceMapper();
    }

    @Bean
    public GenderRepository genderRepository(
            GenderJpaRepository jpaRepository,
            GenderPersistenceMapper mapper) {
        return new GenderRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterGenderUseCase registerGenderUseCase(GenderRepository repository) {
        return new RegisterGenderUseCase(repository);
    }

    @Bean
    public GetGenderByIdUseCase getGenderByIdUseCase(GenderRepository repository) {
        return new GetGenderByIdUseCase(repository);
    }

    @Bean
    public ListGenderUseCase listGenderUseCase(GenderRepository repository) {
        return new ListGenderUseCase(repository);
    }

    @Bean
    public UpdateGenderUseCase updateGenderUseCase(GenderRepository repository) {
        return new UpdateGenderUseCase(repository);
    }

    @Bean
    public DeleteGenderUseCase deleteGenderUseCase(GenderRepository repository) {
        return new DeleteGenderUseCase(repository);
    }
}