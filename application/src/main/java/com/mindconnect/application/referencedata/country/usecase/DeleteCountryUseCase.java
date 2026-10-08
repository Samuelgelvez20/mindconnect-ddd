package com.mindconnect.application.referencedata.country.usecase;

import com.mindconnect.application.referencedata.country.exception.CountryNotFoundApplicationException;
import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;
import com.mindconnect.domain.referencedata.country.port.repository.CountryRepository;

public class DeleteCountryUseCase {

    private final CountryRepository countryRepository;

    public DeleteCountryUseCase(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public void execute(CountryId id) {

        var country = countryRepository.findById(id)
                .orElseThrow(() -> new CountryNotFoundApplicationException(id));

        country.delete();
        countryRepository.delete(country);
    }
}
