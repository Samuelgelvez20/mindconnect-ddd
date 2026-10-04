package com.mindconnect.application.referencedata.citymunicipality.usecase;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.mindconnect.domain.referencedata.citymunicipality.model.aggregate.CityMunicipality;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;
import com.mindconnect.domain.referencedata.citymunicipality.port.repository.CityMunicipalityRepository;
import com.mindconnect.domain.referencedata.stateregion.model.valueobject.StateRegionId;

/** In-memory repository shared by the use case tests (no mocking framework needed). */
final class FakeCityMunicipalityRepository implements CityMunicipalityRepository {

    private final Map<CityMunicipalityId, CityMunicipality> store = new LinkedHashMap<>();
    private final List<CityMunicipality> deleted = new ArrayList<>();

    FakeCityMunicipalityRepository with(CityMunicipality... cityMunicipalities) {
        for (CityMunicipality cityMunicipality : cityMunicipalities) {
            store.put(cityMunicipality.id(), cityMunicipality);
        }
        return this;
    }

    List<CityMunicipality> deleted() {
        return deleted;
    }

    int size() {
        return store.size();
    }

    @Override
    public CityMunicipality save(CityMunicipality cityMunicipality) {
        store.put(cityMunicipality.id(), cityMunicipality);
        return cityMunicipality;
    }

    @Override
    public Optional<CityMunicipality> findById(CityMunicipalityId id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<CityMunicipality> findAll() {
        return List.copyOf(store.values());
    }

    @Override
    public boolean existsByRegionIdAndCode(StateRegionId regionId, String code) {
        return store.values().stream()
                .anyMatch(c -> c.regionId().equals(regionId) && c.code().equals(code));
    }

    @Override
    public boolean existsByRegionIdAndCodeAndIdNot(StateRegionId regionId, String code, CityMunicipalityId id) {
        return store.values().stream()
                .anyMatch(c -> c.regionId().equals(regionId) && c.code().equals(code) && !c.id().equals(id));
    }

    @Override
    public void delete(CityMunicipality cityMunicipality) {
        store.remove(cityMunicipality.id());
        deleted.add(cityMunicipality);
    }
}