package com.mindconnect.domain.clinicalrecord.clinicalnote.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.clinicalrecord.clinicalnote.model.aggregate.ClinicalNote;
import com.mindconnect.domain.clinicalrecord.clinicalnote.model.valueobject.ClinicalNoteId;
import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;

public interface ClinicalNoteRepository {

    ClinicalNote save(ClinicalNote clinicalNote);

    Optional<ClinicalNote> findById(ClinicalNoteId id);

    List<ClinicalNote> findAll();

    List<ClinicalNote> findByEncounterId(EncounterId encounterId);

    void delete(ClinicalNote clinicalNote);
}