package com.mindconnect.application.professional.professionalstudy.usecase;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.mindconnect.domain.professional.professionalstudy.model.aggregate.ProfessionalStudy;
import com.mindconnect.domain.professional.professionalstudy.model.valueobject.ProfessionalStudyId;
import com.mindconnect.domain.professional.professionalstudy.port.repository.ProfessionalStudyRepository;

/** In-memory repository shared by the use case tests (no mocking framework needed). */
final class FakeProfessionalStudyRepository implements ProfessionalStudyRepository {

    private final Map<ProfessionalStudyId, ProfessionalStudy> store = new LinkedHashMap<>();
    private final List<ProfessionalStudy> deleted = new ArrayList<>();

    FakeProfessionalStudyRepository with(ProfessionalStudy... professionalStudies) {
        for (ProfessionalStudy professionalStudy : professionalStudies) {
            store.put(professionalStudy.id(), professionalStudy);
        }
        return this;
    }

    List<ProfessionalStudy> deleted() {
        return deleted;
    }

    int size() {
        return store.size();
    }

    @Override
    public ProfessionalStudy save(ProfessionalStudy professionalStudy) {
        store.put(professionalStudy.id(), professionalStudy);
        return professionalStudy;
    }

    @Override
    public Optional<ProfessionalStudy> findById(ProfessionalStudyId id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<ProfessionalStudy> findAll() {
        return List.copyOf(store.values());
    }

    @Override
    public void delete(ProfessionalStudy professionalStudy) {
        store.remove(professionalStudy.id());
        deleted.add(professionalStudy);
    }
}