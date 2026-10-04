package com.mindconnect.application.referencedata.citymunicipality.usecase;

import com.mindconnect.application.referencedata.citymunicipality.exception.CityMunicipalityNotFoundApplicationException;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;
import com.mindconnect.domain.referencedata.citymunicipality.port.repository.CityMunicipalityRepository;

public class DeleteCityMunicipalityUseCase {

    private final CityMunicipalityRepository cityMunicipalityRepository;

    public DeleteCityMunicipalityUseCase(CityMunicipalityRepository cityMunicipalityRepository) {
        this.cityMunicipalityRepository = cityMunicipalityRepository;
    }

    public void execute(CityMunicipalityId id) {

        var cityMunicipality = cityMunicipalityRepository.findById(id)
                .orElseThrow(() -> new CityMunicipalityNotFoundApplicationException(id));

        cityMunicipality.delete();
        cityMunicipalityRepository.delete(cityMunicipality);
    }
}