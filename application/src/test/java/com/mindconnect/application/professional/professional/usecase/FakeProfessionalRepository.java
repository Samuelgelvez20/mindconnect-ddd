package com.mindconnect.application.professional.professional.usecase;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.mindconnect.domain.professional.professional.model.aggregate.Professional;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.professional.professional.port.repository.ProfessionalRepository;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;

/** In-memory repository shared by the use case tests (no mocking framework needed). */
final class FakeProfessionalRepository implements ProfessionalRepository {

    private final Map<ProfessionalId, Professional> store = new LinkedHashMap<>();
    private final List<Professional> deleted = new ArrayList<>();

    FakeProfessionalRepository with(Professional... professionals) {
        for (Professional professional : professionals) {
            store.put(professional.id(), professional);
        }
        return this;
    }

    List<Professional> deleted() {
        return deleted;
    }

    int size() {
        return store.size();
    }

    @Override
    public Professional save(Professional professional) {
        store.put(professional.id(), professional);
        return professional;
    }

    @Override
    public Optional<Professional> findById(ProfessionalId id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Professional> findAll() {
        return List.copyOf(store.values());
    }

    @Override
    public boolean existsByLicenseNumber(String licenseNumber) {
        return store.values().stream().anyMatch(p -> p.licenseNumber().equals(licenseNumber));
    }

    @Override
    public boolean existsByLicenseNumberAndIdNot(String licenseNumber, ProfessionalId id) {
        return store.values().stream()
                .anyMatch(p -> p.licenseNumber().equals(licenseNumber) && !p.id().equals(id));
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
            ProfessionalId id) {
        return store.values().stream()
                .anyMatch(p -> p.documentTypeId().equals(documentTypeId)
                        && p.documentNumber().equals(documentNumber)
                        && !p.id().equals(id));
    }

    @Override
    public void delete(Professional professional) {
        store.remove(professional.id());
        deleted.add(professional);
    }
}