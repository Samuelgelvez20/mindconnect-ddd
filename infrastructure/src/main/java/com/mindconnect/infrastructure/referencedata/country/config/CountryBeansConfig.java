package com.mindconnect.infrastructure.referencedata.country.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.referencedata.country.usecase.DeleteCountryUseCase;
import com.mindconnect.application.referencedata.country.usecase.GetCountryByIdUseCase;
import com.mindconnect.application.referencedata.country.usecase.ListCountryUseCase;
import com.mindconnect.application.referencedata.country.usecase.RegisterCountryUseCase;
import com.mindconnect.application.referencedata.country.usecase.UpdateCountryUseCase;
import com.mindconnect.domain.referencedata.country.port.repository.CountryRepository;
import com.mindconnect.infrastructure.referencedata.country.adapters.out.persistence.mappers.CountryPersistenceMapper;
import com.mindconnect.infrastructure.referencedata.country.adapters.out.persistence.repositories.CountryJpaRepository;
import com.mindconnect.infrastructure.referencedata.country.adapters.out.persistence.repositories.CountryRepositoryAdapter;

@Configuration
public class CountryBeansConfig {

    @Bean
    public CountryPersistenceMapper countryPersistenceMapper() {
        return new CountryPersistenceMapper();
    }

    @Bean
    public CountryRepository countryRepository(
            CountryJpaRepository jpaRepository,
            CountryPersistenceMapper mapper) {
        return new CountryRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterCountryUseCase registerCountryUseCase(CountryRepository repository) {
        return new RegisterCountryUseCase(repository);
    }

    @Bean
    public GetCountryByIdUseCase getCountryByIdUseCase(CountryRepository repository) {
        return new GetCountryByIdUseCase(repository);
    }

    @Bean
    public ListCountryUseCase listCountryUseCase(CountryRepository repository) {
        return new ListCountryUseCase(repository);
    }

    @Bean
    public UpdateCountryUseCase updateCountryUseCase(CountryRepository repository) {
        return new UpdateCountryUseCase(repository);
    }

    @Bean
    public DeleteCountryUseCase deleteCountryUseCase(CountryRepository repository) {
        return new DeleteCountryUseCase(repository);
    }
}
