package com.mindconnect.application.referencedata.citymunicipality.usecase;

import com.mindconnect.application.referencedata.citymunicipality.command.RegisterCityMunicipalityCommand;
import com.mindconnect.application.referencedata.citymunicipality.dto.CityMunicipalityResponse;
import com.mindconnect.application.referencedata.citymunicipality.exception.CityMunicipalityAlreadyExistsApplicationException;
import com.mindconnect.domain.referencedata.citymunicipality.model.aggregate.CityMunicipality;
import com.mindconnect.domain.referencedata.citymunicipality.port.repository.CityMunicipalityRepository;
import com.mindconnect.domain.referencedata.stateregion.model.valueobject.StateRegionId;

public class RegisterCityMunicipalityUseCase {

    private final CityMunicipalityRepository cityMunicipalityRepository;

    public RegisterCityMunicipalityUseCase(CityMunicipalityRepository cityMunicipalityRepository) {
        this.cityMunicipalityRepository = cityMunicipalityRepository;
    }

    public CityMunicipalityResponse execute(RegisterCityMunicipalityCommand command) {

        CityMunicipality cityMunicipality = CityMunicipality.register(
                command.name(),
                command.code(),
                command.description(),
                command.regionId());

        if (cityMunicipalityRepository.existsByRegionIdAndCode(cityMunicipality.regionId(), cityMunicipality.code())) {
            throw new CityMunicipalityAlreadyExistsApplicationException(cityMunicipality.regionId(), cityMunicipality.code());
        }

        return CityMunicipalityResponse.from(cityMunicipalityRepository.save(cityMunicipality));
    }
}