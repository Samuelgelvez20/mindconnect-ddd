package com.mindconnect.application.professional.professionaltype.usecase;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.mindconnect.domain.professional.professionaltype.model.aggregate.ProfessionalType;
import com.mindconnect.domain.professional.professionaltype.model.valueobject.ProfessionalTypeId;
import com.mindconnect.domain.professional.professionaltype.port.repository.ProfessionalTypeRepository;

/** In-memory repository shared by the use case tests (no mocking framework needed). */
final class FakeProfessionalTypeRepository implements ProfessionalTypeRepository {

    private final Map<ProfessionalTypeId, ProfessionalType> store = new LinkedHashMap<>();
    private final List<ProfessionalType> deleted = new ArrayList<>();

    FakeProfessionalTypeRepository with(ProfessionalType... professionalTypes) {
        for (ProfessionalType professionalType : professionalTypes) {
            store.put(professionalType.id(), professionalType);
        }
        return this;
    }

    List<ProfessionalType> deleted() {
        return deleted;
    }

    int size() {
        return store.size();
    }

    @Override
    public ProfessionalType save(ProfessionalType professionalType) {
        store.put(professionalType.id(), professionalType);
        return professionalType;
    }

    @Override
    public Optional<ProfessionalType> findById(ProfessionalTypeId id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<ProfessionalType> findAll() {
        return List.copyOf(store.values());
    }

    @Override
    public boolean existsByName(String name) {
        return store.values().stream().anyMatch(p -> p.name().equals(name));
    }

    @Override
    public boolean existsByNameAndIdNot(String name, ProfessionalTypeId id) {
        return store.values().stream()
                .anyMatch(p -> p.name().equals(name) && !p.id().equals(id));
    }

    @Override
    public void delete(ProfessionalType professionalType) {
        store.remove(professionalType.id());
        deleted.add(professionalType);
    }
}