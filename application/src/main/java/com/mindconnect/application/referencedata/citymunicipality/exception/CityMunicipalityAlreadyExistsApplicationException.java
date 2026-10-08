package com.mindconnect.application.referencedata.citymunicipality.exception;

import com.mindconnect.application.common.exception.AlreadyExistsApplicationException;
import com.mindconnect.domain.referencedata.stateregion.model.valueobject.StateRegionId;

public class CityMunicipalityAlreadyExistsApplicationException extends AlreadyExistsApplicationException {

    public CityMunicipalityAlreadyExistsApplicationException(StateRegionId regionId, String code) {
        super("CityMunicipality already exists with regionId: " + regionId.value() + " and code: " + code);
    }
}