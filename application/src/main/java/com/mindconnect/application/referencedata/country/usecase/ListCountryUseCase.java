package com.mindconnect.application.referencedata.country.usecase;

import java.util.List;

import com.mindconnect.application.referencedata.country.dto.CountryResponse;
import com.mindconnect.domain.referencedata.country.port.repository.CountryRepository;

public class ListCountryUseCase {

    private final CountryRepository countryRepository;

    public ListCountryUseCase(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<CountryResponse> execute() {
        return countryRepository.findAll()
                .stream()
                .map(CountryResponse::from)
                .toList();
    }
}
