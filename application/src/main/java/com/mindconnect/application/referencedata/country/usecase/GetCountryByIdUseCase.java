package com.mindconnect.application.referencedata.country.usecase;

import com.mindconnect.application.referencedata.country.dto.CountryResponse;
import com.mindconnect.application.referencedata.country.exception.CountryNotFoundApplicationException;
import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;
import com.mindconnect.domain.referencedata.country.port.repository.CountryRepository;

public class GetCountryByIdUseCase {

    private final CountryRepository countryRepository;

    public GetCountryByIdUseCase(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public CountryResponse execute(CountryId id) {
        return countryRepository.findById(id)
                .map(CountryResponse::from)
                .orElseThrow(() -> new CountryNotFoundApplicationException(id));
    }
}
