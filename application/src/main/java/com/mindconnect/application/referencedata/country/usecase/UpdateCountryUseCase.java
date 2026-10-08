package com.mindconnect.application.referencedata.country.usecase;

import com.mindconnect.application.referencedata.country.command.UpdateCountryCommand;
import com.mindconnect.application.referencedata.country.dto.CountryResponse;
import com.mindconnect.application.referencedata.country.exception.CountryAlreadyExistsApplicationException;
import com.mindconnect.application.referencedata.country.exception.CountryNotFoundApplicationException;
import com.mindconnect.domain.referencedata.country.port.repository.CountryRepository;

public class UpdateCountryUseCase {

    private final CountryRepository countryRepository;

    public UpdateCountryUseCase(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public CountryResponse execute(UpdateCountryCommand command) {

        var country = countryRepository.findById(command.id())
                .orElseThrow(() -> new CountryNotFoundApplicationException(command.id()));

        country.update(
                command.name(),
                command.code(),
                command.description(),
                command.telephonePrefix(),
                command.active()
        );

        if (countryRepository.existsByCodeAndIdNot(country.code(), country.id())) {
            throw new CountryAlreadyExistsApplicationException(country.code());
        }

        return CountryResponse.from(countryRepository.save(country));
    }
}
