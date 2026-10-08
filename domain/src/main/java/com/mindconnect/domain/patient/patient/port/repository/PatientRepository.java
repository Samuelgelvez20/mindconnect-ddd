package com.mindconnect.domain.patient.patient.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.patient.patient.model.aggregate.Patient;
import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;

public interface PatientRepository {

    Patient save(Patient patient);

    Optional<Patient> findById(PatientId id);

    List<Patient> findAll();

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, PatientId id);

    boolean existsByDocumentTypeIdAndDocumentNumber(DocumentTypeId documentTypeId, String documentNumber);

    boolean existsByDocumentTypeIdAndDocumentNumberAndIdNot(DocumentTypeId documentTypeId, String documentNumber, PatientId id);

    void delete(Patient patient);
}