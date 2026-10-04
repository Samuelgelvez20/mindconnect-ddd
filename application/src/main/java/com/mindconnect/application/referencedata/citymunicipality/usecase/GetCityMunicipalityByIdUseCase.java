package com.mindconnect.application.referencedata.citymunicipality.usecase;

import com.mindconnect.application.referencedata.citymunicipality.dto.CityMunicipalityResponse;
import com.mindconnect.application.referencedata.citymunicipality.exception.CityMunicipalityNotFoundApplicationException;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;
import com.mindconnect.domain.referencedata.citymunicipality.port.repository.CityMunicipalityRepository;

public class GetCityMunicipalityByIdUseCase {

    private final CityMunicipalityRepository cityMunicipalityRepository;

    public GetCityMunicipalityByIdUseCase(CityMunicipalityRepository cityMunicipalityRepository) {
        this.cityMunicipalityRepository = cityMunicipalityRepository;
    }

    public CityMunicipalityResponse execute(CityMunicipalityId id) {
        return cityMunicipalityRepository.findById(id)
                .map(CityMunicipalityResponse::from)
                .orElseThrow(() -> new CityMunicipalityNotFoundApplicationException(id));
    }
}