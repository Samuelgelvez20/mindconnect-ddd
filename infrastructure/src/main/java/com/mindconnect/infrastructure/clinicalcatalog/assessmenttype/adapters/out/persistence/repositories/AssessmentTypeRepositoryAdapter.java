package com.mindconnect.infrastructure.clinicalcatalog.assessmenttype.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.clinicalcatalog.assessmenttype.model.aggregate.AssessmentType;
import com.mindconnect.domain.clinicalcatalog.assessmenttype.model.valueobject.AssessmentTypeId;
import com.mindconnect.domain.clinicalcatalog.assessmenttype.port.repository.AssessmentTypeRepository;
import com.mindconnect.infrastructure.clinicalcatalog.assessmenttype.adapters.out.persistence.mappers.AssessmentTypePersistenceMapper;

public class AssessmentTypeRepositoryAdapter implements AssessmentTypeRepository {

    private final AssessmentTypeJpaRepository jpaRepository;
    private final AssessmentTypePersistenceMapper mapper;

    public AssessmentTypeRepositoryAdapter(
            AssessmentTypeJpaRepository jpaRepository,
            AssessmentTypePersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public AssessmentType save(AssessmentType assessmentType) {
        return mapper.toDomain(jpaRepository.save(mapper.toJpa(assessmentType)));
    }

    @Override
    public Optional<AssessmentType> findById(AssessmentTypeId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public Optional<AssessmentType> findByCode(String code) {
        return jpaRepository.findByCode(code).map(mapper::toDomain);
    }

    @Override
    public List<AssessmentType> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existsByCodeAndIdNot(String code, AssessmentTypeId id) {
        return jpaRepository.existsByCodeAndIdNot(code, id.value());
    }

    @Override
    public void delete(AssessmentType assessmentType) {
        jpaRepository.deleteById(assessmentType.id().value());
    }
}