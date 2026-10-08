package com.mindconnect.infrastructure.clinicalrecord.risklevel.adapters.out.persistence.repositories;

import com.mindconnect.domain.clinicalrecord.risklevel.model.aggregate.RiskLevel;
import com.mindconnect.domain.clinicalrecord.risklevel.model.valueobject.RiskLevelId;
import com.mindconnect.domain.clinicalrecord.risklevel.port.repository.RiskLevelRepository;
import com.mindconnect.infrastructure.clinicalrecord.risklevel.adapters.out.persistence.entity.RiskLevelJpaEntity;
import com.mindconnect.infrastructure.clinicalrecord.risklevel.adapters.out.persistence.mappers.RiskLevelPersistenceMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RiskLevelJpaRepository extends JpaRepository<RiskLevelJpaEntity, UUID> {

    Optional<RiskLevelJpaEntity> findByCode(String code);

    Optional<RiskLevelJpaEntity> findByActiveTrue();
}