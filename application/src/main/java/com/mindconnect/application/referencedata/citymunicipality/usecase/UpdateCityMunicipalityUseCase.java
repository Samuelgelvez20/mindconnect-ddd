package com.mindconnect.application.referencedata.citymunicipality.usecase;

import com.mindconnect.application.referencedata.citymunicipality.command.UpdateCityMunicipalityCommand;
import com.mindconnect.application.referencedata.citymunicipality.dto.CityMunicipalityResponse;
import com.mindconnect.application.referencedata.citymunicipality.exception.CityMunicipalityAlreadyExistsApplicationException;
import com.mindconnect.application.referencedata.citymunicipality.exception.CityMunicipalityNotFoundApplicationException;
import com.mindconnect.domain.referencedata.stateregion.model.valueobject.StateRegionId;
import com.mindconnect.domain.referencedata.citymunicipality.port.repository.CityMunicipalityRepository;

public class UpdateCityMunicipalityUseCase {

    private final CityMunicipalityRepository cityMunicipalityRepository;

    public UpdateCityMunicipalityUseCase(CityMunicipalityRepository cityMunicipalityRepository) {
        this.cityMunicipalityRepository = cityMunicipalityRepository;
    }

    public CityMunicipalityResponse execute(UpdateCityMunicipalityCommand command) {

        var cityMunicipality = cityMunicipalityRepository.findById(command.id())
                .orElseThrow(() -> new CityMunicipalityNotFoundApplicationException(command.id()));

        cityMunicipality.update(command.name(), command.code(), command.description(), command.active());

        if (cityMunicipalityRepository.existsByRegionIdAndCodeAndIdNot(cityMunicipality.regionId(), cityMunicipality.code(), cityMunicipality.id())) {
            throw new CityMunicipalityAlreadyExistsApplicationException(cityMunicipality.regionId(), cityMunicipality.code());
        }

        return CityMunicipalityResponse.from(cityMunicipalityRepository.save(cityMunicipality));
    }
}