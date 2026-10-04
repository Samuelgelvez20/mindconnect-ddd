package com.mindconnect.application.referencedata.stateregion.usecase;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;
import com.mindconnect.domain.referencedata.stateregion.model.aggregate.StateRegion;
import com.mindconnect.domain.referencedata.stateregion.model.valueobject.StateRegionId;
import com.mindconnect.domain.referencedata.stateregion.port.repository.StateRegionRepository;

/** In-memory repository shared by the use case tests (no mocking framework needed). */
final class FakeStateRegionRepository implements StateRegionRepository {

    private final Map<StateRegionId, StateRegion> store = new LinkedHashMap<>();
    private final List<StateRegion> deleted = new ArrayList<>();

    FakeStateRegionRepository with(StateRegion... stateRegions) {
        for (StateRegion stateRegion : stateRegions) {
            store.put(stateRegion.id(), stateRegion);
        }
        return this;
    }

    List<StateRegion> deleted() {
        return deleted;
    }

    int size() {
        return store.size();
    }

    @Override
    public StateRegion save(StateRegion stateRegion) {
        store.put(stateRegion.id(), stateRegion);
        return stateRegion;
    }

    @Override
    public Optional<StateRegion> findById(StateRegionId id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<StateRegion> findAll() {
        return List.copyOf(store.values());
    }

    @Override
    public boolean existsByCountryIdAndCode(CountryId countryId, String code) {
        return store.values().stream()
                .anyMatch(s -> s.countryId().equals(countryId) && s.code().equals(code));
    }

    @Override
    public boolean existsByCountryIdAndCodeAndIdNot(CountryId countryId, String code, StateRegionId id) {
        return store.values().stream()
                .anyMatch(s -> s.countryId().equals(countryId) && s.code().equals(code) && !s.id().equals(id));
    }

    @Override
    public void delete(StateRegion stateRegion) {
        store.remove(stateRegion.id());
        deleted.add(stateRegion);
    }
}