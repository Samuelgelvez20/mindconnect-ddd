package com.mindconnect.infrastructure.clinicalrecord.risklevel.adapters.out.persistence.repositories;

import com.mindconnect.domain.clinicalrecord.risklevel.model.aggregate.RiskLevel;
import com.mindconnect.domain.clinicalrecord.risklevel.model.valueobject.RiskLevelId;
import com.mindconnect.domain.clinicalrecord.risklevel.port.repository.RiskLevelRepository;
import com.mindconnect.infrastructure.clinicalrecord.risklevel.adapters.out.persistence.entity.RiskLevelJpaEntity;
import com.mindconnect.infrastructure.clinicalrecord.risklevel.adapters.out.persistence.mappers.RiskLevelPersistenceMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RiskLevelRepositoryAdapter implements RiskLevelRepository {

    private final RiskLevelJpaRepository jpaRepository;
    private final RiskLevelPersistenceMapper mapper;

    public RiskLevelRepositoryAdapter(RiskLevelJpaRepository jpaRepository, RiskLevelPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public RiskLevel save(RiskLevel riskLevel) {
        RiskLevelJpaEntity entity = mapper.toJpa(riskLevel);
        RiskLevelJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<RiskLevel> findById(RiskLevelId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<RiskLevel> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByCode(String code) {
        return jpaRepository.findByCode(code).isPresent();
    }

    @Override
    public boolean existsByCodeAndIdNot(String code, RiskLevelId id) {
        return jpaRepository.findByCode(code).filter(e -> !e.getId().equals(id.value())).isPresent();
    }

    @Override
    public void delete(RiskLevel riskLevel) {
        jpaRepository.deleteById(riskLevel.id().value());
    }
}