package com.mindconnect.domain.referencedata.stateregion.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;
import com.mindconnect.domain.referencedata.stateregion.model.aggregate.StateRegion;
import com.mindconnect.domain.referencedata.stateregion.model.valueobject.StateRegionId;

public interface StateRegionRepository {

    StateRegion save(StateRegion stateRegion);

    Optional<StateRegion> findById(StateRegionId id);

    List<StateRegion> findAll();

    boolean existsByCountryIdAndCode(CountryId countryId, String code);

    boolean existsByCountryIdAndCodeAndIdNot(CountryId countryId, String code, StateRegionId id);

    void delete(StateRegion stateRegion);
}