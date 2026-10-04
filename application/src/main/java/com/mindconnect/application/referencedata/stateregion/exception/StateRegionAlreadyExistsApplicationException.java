package com.mindconnect.application.referencedata.stateregion.exception;

import com.mindconnect.application.common.exception.AlreadyExistsApplicationException;
import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;

public class StateRegionAlreadyExistsApplicationException extends AlreadyExistsApplicationException {

    public StateRegionAlreadyExistsApplicationException(CountryId countryId, String code) {
        super("StateRegion already exists with countryId: " + countryId.value() + " and code: " + code);
    }
}