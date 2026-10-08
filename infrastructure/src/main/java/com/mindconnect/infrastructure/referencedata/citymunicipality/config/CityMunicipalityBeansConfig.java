package com.mindconnect.infrastructure.referencedata.citymunicipality.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindconnect.application.referencedata.citymunicipality.usecase.DeleteCityMunicipalityUseCase;
import com.mindconnect.application.referencedata.citymunicipality.usecase.GetCityMunicipalityByIdUseCase;
import com.mindconnect.application.referencedata.citymunicipality.usecase.ListCityMunicipalityUseCase;
import com.mindconnect.application.referencedata.citymunicipality.usecase.RegisterCityMunicipalityUseCase;
import com.mindconnect.application.referencedata.citymunicipality.usecase.UpdateCityMunicipalityUseCase;
import com.mindconnect.domain.referencedata.citymunicipality.port.repository.CityMunicipalityRepository;
import com.mindconnect.infrastructure.referencedata.citymunicipality.adapters.out.persistence.mappers.CityMunicipalityPersistenceMapper;
import com.mindconnect.infrastructure.referencedata.citymunicipality.adapters.out.persistence.repositories.CityMunicipalityJpaRepository;
import com.mindconnect.infrastructure.referencedata.citymunicipality.adapters.out.persistence.repositories.CityMunicipalityRepositoryAdapter;

@Configuration
public class CityMunicipalityBeansConfig {

    @Bean
    public CityMunicipalityPersistenceMapper cityMunicipalityPersistenceMapper() {
        return new CityMunicipalityPersistenceMapper();
    }

    @Bean
    public CityMunicipalityRepository cityMunicipalityRepository(
            CityMunicipalityJpaRepository jpaRepository,
            CityMunicipalityPersistenceMapper mapper) {
        return new CityMunicipalityRepositoryAdapter(jpaRepository, mapper);
    }

    @Bean
    public RegisterCityMunicipalityUseCase registerCityMunicipalityUseCase(CityMunicipalityRepository repository) {
        return new RegisterCityMunicipalityUseCase(repository);
    }

    @Bean
    public GetCityMunicipalityByIdUseCase getCityMunicipalityByIdUseCase(CityMunicipalityRepository repository) {
        return new GetCityMunicipalityByIdUseCase(repository);
    }

    @Bean
    public ListCityMunicipalityUseCase listCityMunicipalityUseCase(CityMunicipalityRepository repository) {
        return new ListCityMunicipalityUseCase(repository);
    }

    @Bean
    public UpdateCityMunicipalityUseCase updateCityMunicipalityUseCase(CityMunicipalityRepository repository) {
        return new UpdateCityMunicipalityUseCase(repository);
    }

    @Bean
    public DeleteCityMunicipalityUseCase deleteCityMunicipalityUseCase(CityMunicipalityRepository repository) {
        return new DeleteCityMunicipalityUseCase(repository);
    }
}