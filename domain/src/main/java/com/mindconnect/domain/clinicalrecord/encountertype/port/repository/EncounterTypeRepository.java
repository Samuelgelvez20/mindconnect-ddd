package com.mindconnect.domain.clinicalrecord.encountertype.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.clinicalrecord.encountertype.model.aggregate.EncounterType;
import com.mindconnect.domain.clinicalrecord.encountertype.model.valueobject.EncounterTypeId;

public interface EncounterTypeRepository {

    EncounterType save(EncounterType encounterType);

    Optional<EncounterType> findById(EncounterTypeId id);

    List<EncounterType> findAll();

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, EncounterTypeId id);

    void delete(EncounterType encounterType);
}