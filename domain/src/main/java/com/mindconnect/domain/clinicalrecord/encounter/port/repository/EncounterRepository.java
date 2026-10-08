package com.mindconnect.domain.clinicalrecord.encounter.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.clinicalrecord.encounter.model.aggregate.Encounter;
import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;
import com.mindconnect.domain.clinicalrecord.clinicalrecord.model.valueobject.ClinicalRecordId;

public interface EncounterRepository {

    Encounter save(Encounter encounter);

    Optional<Encounter> findById(EncounterId id);

    List<Encounter> findAll();

    List<Encounter> findByClinicalRecordId(ClinicalRecordId clinicalRecordId);

    void delete(Encounter encounter);
}