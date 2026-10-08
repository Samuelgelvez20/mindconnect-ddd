package com.mindconnect.application.patient.patient.usecase;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.mindconnect.domain.patient.patient.model.aggregate.Patient;
import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;
import com.mindconnect.domain.patient.patient.port.repository.PatientRepository;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;

/** In-memory repository shared by the use case tests (no mocking framework needed). */
final class FakePatientRepository implements PatientRepository {

    private final Map<PatientId, Patient> store = new LinkedHashMap<>();
    private final List<Patient> deleted = new ArrayList<>();

    FakePatientRepository with(Patient... patients) {
        for (Patient patient : patients) {
            store.put(patient.id(), patient);
        }
        return this;
    }

    List<Patient> deleted() {
        return deleted;
    }

    int size() {
        return store.size();
    }

    @Override
    public Patient save(Patient patient) {
        store.put(patient.id(), patient);
        return patient;
    }

    @Override
    public Optional<Patient> findById(PatientId id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Patient> findAll() {
        return List.copyOf(store.values());
    }

    @Override
    public boolean existsByEmail(String email) {
        return store.values().stream().anyMatch(p -> p.email().equals(email));
    }

    @Override
    public boolean existsByEmailAndIdNot(String email, PatientId id) {
        return store.values().stream()
                .anyMatch(p -> p.email().equals(email) && !p.id().equals(id));
    }

    @Override
    public boolean existsByDocumentTypeIdAndDocumentNumber(
            com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId documentTypeId,
            String documentNumber) {
        return store.values().stream()
                .anyMatch(p -> p.documentTypeId().equals(documentTypeId)
                        && p.documentNumber().equals(documentNumber));
    }

    @Override
    public boolean existsByDocumentTypeIdAndDocumentNumberAndIdNot(
            com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId documentTypeId,
            String documentNumber,
            PatientId id) {
        return store.values().stream()
                .anyMatch(p -> p.documentTypeId().equals(documentTypeId)
                        && p.documentNumber().equals(documentNumber)
                        && !p.id().equals(id));
    }

    @Override
    public void delete(Patient patient) {
        store.remove(patient.id());
        deleted.add(patient);
    }
}