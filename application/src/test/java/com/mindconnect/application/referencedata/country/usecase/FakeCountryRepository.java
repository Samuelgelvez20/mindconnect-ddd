package com.mindconnect.application.referencedata.country.usecase;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.mindconnect.domain.referencedata.country.model.aggregate.Country;
import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;
import com.mindconnect.domain.referencedata.country.port.repository.CountryRepository;

/** In-memory repository shared by the use case tests (no mocking framework needed). */
final class FakeCountryRepository implements CountryRepository {

    private final Map<CountryId, Country> store = new LinkedHashMap<>();
    private final List<Country> deleted = new ArrayList<>();

    FakeCountryRepository with(Country... countries) {
        for (Country country : countries) {
            store.put(country.id(), country);
        }
        return this;
    }

    List<Country> deleted() {
        return deleted;
    }

    int size() {
        return store.size();
    }

    @Override
    public Country save(Country country) {
        store.put(country.id(), country);
        return country;
    }

    @Override
    public Optional<Country> findById(CountryId id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Country> findAll() {
        return List.copyOf(store.values());
    }

    @Override
    public boolean existsByCode(String code) {
        return store.values().stream().anyMatch(c -> c.code().equals(code));
    }

    @Override
    public boolean existsByCodeAndIdNot(String code, CountryId id) {
        return store.values().stream()
                .anyMatch(c -> c.code().equals(code) && !c.id().equals(id));
    }

    @Override
    public void delete(Country country) {
        store.remove(country.id());
        deleted.add(country);
    }
}
