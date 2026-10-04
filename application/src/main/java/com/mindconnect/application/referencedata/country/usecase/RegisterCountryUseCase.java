package com.mindconnect.application.referencedata.country.usecase;

import com.mindconnect.application.referencedata.country.command.RegisterCountryCommand;
import com.mindconnect.application.referencedata.country.dto.CountryResponse;
import com.mindconnect.application.referencedata.country.exception.CountryAlreadyExistsApplicationException;
import com.mindconnect.domain.referencedata.country.model.aggregate.Country;
import com.mindconnect.domain.referencedata.country.port.repository.CountryRepository;

public class RegisterCountryUseCase {

    private final CountryRepository countryRepository;

    public RegisterCountryUseCase(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public CountryResponse execute(RegisterCountryCommand command) {

        // The aggregate validates and normalizes (trim) the values first.
        Country country = Country.register(
                command.name(),
                command.code(),
                command.description(),
                command.telephonePrefix()
        );

        if (countryRepository.existsByCode(country.code())) {
            throw new CountryAlreadyExistsApplicationException(country.code());
        }

        return CountryResponse.from(countryRepository.save(country));
    }
}
