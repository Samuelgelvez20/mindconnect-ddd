package com.mindconnect.application.patient.patientallergy.usecase;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.mindconnect.domain.patient.patientallergy.model.aggregate.PatientAllergy;
import com.mindconnect.domain.patient.patientallergy.model.valueobject.PatientAllergyId;
import com.mindconnect.domain.patient.patientallergy.port.repository.PatientAllergyRepository;

/** In-memory repository shared by the use case tests (no mocking framework needed). */
final class FakePatientAllergyRepository implements PatientAllergyRepository {

    private final Map<PatientAllergyId, PatientAllergy> store = new LinkedHashMap<>();
    private final List<PatientAllergy> deleted = new ArrayList<>();

    FakePatientAllergyRepository with(PatientAllergy... patientAllergies) {
        for (PatientAllergy patientAllergy : patientAllergies) {
            store.put(patientAllergy.id(), patientAllergy);
        }
        return this;
    }

    List<PatientAllergy> deleted() {
        return deleted;
    }

    int size() {
        return store.size();
    }

    @Override
    public PatientAllergy save(PatientAllergy patientAllergy) {
        store.put(patientAllergy.id(), patientAllergy);
        return patientAllergy;
    }

    @Override
    public Optional<PatientAllergy> findById(PatientAllergyId id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<PatientAllergy> findAll() {
        return List.copyOf(store.values());
    }

    @Override
    public void delete(PatientAllergy patientAllergy) {
        store.remove(patientAllergy.id());
        deleted.add(patientAllergy);
    }
}