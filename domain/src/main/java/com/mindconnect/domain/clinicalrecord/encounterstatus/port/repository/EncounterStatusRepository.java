package com.mindconnect.domain.clinicalrecord.encounterstatus.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.clinicalrecord.encounterstatus.model.aggregate.EncounterStatus;
import com.mindconnect.domain.clinicalrecord.encounterstatus.model.valueobject.EncounterStatusId;

public interface EncounterStatusRepository {

    EncounterStatus save(EncounterStatus encounterStatus);

    Optional<EncounterStatus> findById(EncounterStatusId id);

    List<EncounterStatus> findAll();

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, EncounterStatusId id);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, EncounterStatusId id);

    void delete(EncounterStatus encounterStatus);
}