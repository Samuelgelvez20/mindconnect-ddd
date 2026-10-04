package com.mindconnect.application.referencedata.citymunicipality.usecase;

import java.util.List;

import com.mindconnect.application.referencedata.citymunicipality.dto.CityMunicipalityResponse;
import com.mindconnect.domain.referencedata.citymunicipality.port.repository.CityMunicipalityRepository;

public class ListCityMunicipalityUseCase {

    private final CityMunicipalityRepository cityMunicipalityRepository;

    public ListCityMunicipalityUseCase(CityMunicipalityRepository cityMunicipalityRepository) {
        this.cityMunicipalityRepository = cityMunicipalityRepository;
    }

    public List<CityMunicipalityResponse> execute() {
        return cityMunicipalityRepository.findAll()
                .stream()
                .map(CityMunicipalityResponse::from)
                .toList();
    }
}