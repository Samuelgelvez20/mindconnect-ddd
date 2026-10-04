package com.mindconnect.infrastructure.referencedata.gender.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.referencedata.gender.model.aggregate.Gender;
import com.mindconnect.domain.referencedata.gender.model.valueobject.GenderId;
import com.mindconnect.domain.referencedata.gender.port.repository.GenderRepository;
import com.mindconnect.infrastructure.referencedata.gender.adapters.out.persistence.mappers.GenderPersistenceMapper;

public class GenderRepositoryAdapter implements GenderRepository {

    private final GenderJpaRepository genderJpaRepository;
    private final GenderPersistenceMapper mapper;

    public GenderRepositoryAdapter(
            GenderJpaRepository genderJpaRepository,
            GenderPersistenceMapper mapper) {
        this.genderJpaRepository = genderJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Gender save(Gender gender) {
        return mapper.toDomain(genderJpaRepository.save(mapper.toJpa(gender)));
    }

    @Override
    public Optional<Gender> findById(GenderId id) {
        return genderJpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<Gender> findAll() {
        return genderJpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existsByDescription(String description) {
        return genderJpaRepository.existsByDescription(description);
    }

    @Override
    public boolean existsByDescriptionAndIdNot(String description, GenderId id) {
        return genderJpaRepository.existsByDescriptionAndIdNot(description, id.value());
    }

    @Override
    public void delete(Gender gender) {
        genderJpaRepository.deleteById(gender.id().value());
    }
}