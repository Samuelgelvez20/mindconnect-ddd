package com.mindconnect.infrastructure.clinicalrecord.riskassessment.adapters.out.persistence.repositories;

import com.mindconnect.domain.clinicalrecord.riskassessment.model.aggregate.RiskAssessment;
import com.mindconnect.domain.clinicalrecord.riskassessment.model.valueobject.RiskAssessmentId;
import com.mindconnect.domain.clinicalrecord.riskassessment.port.repository.RiskAssessmentRepository;
import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;
import com.mindconnect.infrastructure.clinicalrecord.riskassessment.adapters.out.persistence.entity.RiskAssessmentJpaEntity;
import com.mindconnect.infrastructure.clinicalrecord.riskassessment.adapters.out.persistence.mappers.RiskAssessmentPersistenceMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RiskAssessmentRepositoryAdapter implements RiskAssessmentRepository {

    private final RiskAssessmentJpaRepository jpaRepository;
    private final RiskAssessmentPersistenceMapper mapper;

    public RiskAssessmentRepositoryAdapter(RiskAssessmentJpaRepository jpaRepository, RiskAssessmentPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public com.mindconnect.domain.clinicalrecord.riskassessment.model.aggregate.RiskAssessment save(com.mindconnect.domain.clinicalrecord.riskassessment.model.aggregate.RiskAssessment riskAssessment) {
        RiskAssessmentJpaEntity entity = mapper.toJpa(riskAssessment);
        RiskAssessmentJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<com.mindconnect.domain.clinicalrecord.riskassessment.model.aggregate.RiskAssessment> findById(RiskAssessmentId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<com.mindconnect.domain.clinicalrecord.riskassessment.model.aggregate.RiskAssessment> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<com.mindconnect.domain.clinicalrecord.riskassessment.model.aggregate.RiskAssessment> findByEncounterId(EncounterId encounterId) {
        return jpaRepository.findByEncounterId(encounterId.value()).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(com.mindconnect.domain.clinicalrecord.riskassessment.model.aggregate.RiskAssessment riskAssessment) {
        jpaRepository.deleteById(riskAssessment.id().value());
    }
}