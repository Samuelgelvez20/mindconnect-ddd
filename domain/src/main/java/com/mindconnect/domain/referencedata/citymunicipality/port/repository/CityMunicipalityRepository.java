package com.mindconnect.domain.referencedata.citymunicipality.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.referencedata.citymunicipality.model.aggregate.CityMunicipality;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;
import com.mindconnect.domain.referencedata.stateregion.model.valueobject.StateRegionId;

public interface CityMunicipalityRepository {

    CityMunicipality save(CityMunicipality cityMunicipality);

    Optional<CityMunicipality> findById(CityMunicipalityId id);

    List<CityMunicipality> findAll();

    boolean existsByRegionIdAndCode(StateRegionId regionId, String code);

    boolean existsByRegionIdAndCodeAndIdNot(StateRegionId regionId, String code, CityMunicipalityId id);

    void delete(CityMunicipality cityMunicipality);
}