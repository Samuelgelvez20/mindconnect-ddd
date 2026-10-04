package com.mindconnect.application.referencedata.gender.usecase;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.mindconnect.domain.referencedata.gender.model.aggregate.Gender;
import com.mindconnect.domain.referencedata.gender.model.valueobject.GenderId;
import com.mindconnect.domain.referencedata.gender.port.repository.GenderRepository;

/** In-memory repository shared by the use case tests (no mocking framework needed). */
final class FakeGenderRepository implements GenderRepository {

    private final Map<GenderId, Gender> store = new LinkedHashMap<>();
    private final List<Gender> deleted = new ArrayList<>();

    FakeGenderRepository with(Gender... genders) {
        for (Gender gender : genders) {
            store.put(gender.id(), gender);
        }
        return this;
    }

    List<Gender> deleted() {
        return deleted;
    }

    int size() {
        return store.size();
    }

    @Override
    public Gender save(Gender gender) {
        store.put(gender.id(), gender);
        return gender;
    }

    @Override
    public Optional<Gender> findById(GenderId id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Gender> findAll() {
        return List.copyOf(store.values());
    }

    @Override
    public boolean existsByDescription(String description) {
        return store.values().stream().anyMatch(g -> g.description().equals(description));
    }

    @Override
    public boolean existsByDescriptionAndIdNot(String description, GenderId id) {
        return store.values().stream()
                .anyMatch(g -> g.description().equals(description) && !g.id().equals(id));
    }

    @Override
    public void delete(Gender gender) {
        store.remove(gender.id());
        deleted.add(gender);
    }
}