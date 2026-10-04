package com.mindconnect.application.referencedata.citymunicipality.exception;

import com.mindconnect.application.common.exception.NotFoundApplicationException;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;

public class CityMunicipalityNotFoundApplicationException extends NotFoundApplicationException {

    public CityMunicipalityNotFoundApplicationException(CityMunicipalityId id) {
        super("CityMunicipality not found with id: " + id.value());
    }
}