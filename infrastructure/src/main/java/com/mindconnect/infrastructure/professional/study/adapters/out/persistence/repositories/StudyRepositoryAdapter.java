package com.mindconnect.infrastructure.professional.study.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.professional.study.model.aggregate.Study;
import com.mindconnect.domain.professional.study.model.valueobject.StudyId;
import com.mindconnect.domain.professional.study.port.repository.StudyRepository;
import com.mindconnect.infrastructure.professional.study.adapters.out.persistence.mappers.StudyPersistenceMapper;

public class StudyRepositoryAdapter implements StudyRepository {

    private final StudyJpaRepository studyJpaRepository;
    private final StudyPersistenceMapper mapper;

    public StudyRepositoryAdapter(
            StudyJpaRepository studyJpaRepository,
            StudyPersistenceMapper mapper) {
        this.studyJpaRepository = studyJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Study save(Study study) {
        return mapper.toDomain(studyJpaRepository.save(mapper.toJpa(study)));
    }

    @Override
    public Optional<Study> findById(StudyId id) {
        return studyJpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<Study> findAll() {
        return studyJpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void delete(Study study) {
        studyJpaRepository.deleteById(study.id().value());
    }
}