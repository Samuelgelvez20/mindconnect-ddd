package com.mindconnect.domain.clinicalrecord.encountermodality.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.clinicalrecord.encountermodality.model.aggregate.EncounterModality;
import com.mindconnect.domain.clinicalrecord.encountermodality.model.valueobject.EncounterModalityId;

public interface EncounterModalityRepository {

    EncounterModality save(EncounterModality encounterModality);

    Optional<EncounterModality> findById(EncounterModalityId id);

    List<EncounterModality> findAll();

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, EncounterModalityId id);

    void delete(EncounterModality encounterModality);
}