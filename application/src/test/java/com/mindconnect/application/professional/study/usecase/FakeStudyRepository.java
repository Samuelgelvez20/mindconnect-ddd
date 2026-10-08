package com.mindconnect.application.professional.study.usecase;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.mindconnect.domain.professional.study.model.aggregate.Study;
import com.mindconnect.domain.professional.study.model.valueobject.StudyId;
import com.mindconnect.domain.professional.study.port.repository.StudyRepository;

/** In-memory repository shared by the use case tests (no mocking framework needed). */
final class FakeStudyRepository implements StudyRepository {

    private final Map<StudyId, Study> store = new LinkedHashMap<>();
    private final List<Study> deleted = new ArrayList<>();

    FakeStudyRepository with(Study... studies) {
        for (Study study : studies) {
            store.put(study.id(), study);
        }
        return this;
    }

    List<Study> deleted() {
        return deleted;
    }

    int size() {
        return store.size();
    }

    @Override
    public Study save(Study study) {
        store.put(study.id(), study);
        return study;
    }

    @Override
    public Optional<Study> findById(StudyId id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Study> findAll() {
        return List.copyOf(store.values());
    }

    @Override
    public void delete(Study study) {
        store.remove(study.id());
        deleted.add(study);
    }
}