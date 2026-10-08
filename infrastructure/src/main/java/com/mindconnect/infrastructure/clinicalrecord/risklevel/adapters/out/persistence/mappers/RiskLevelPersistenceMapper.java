package com.mindconnect.infrastructure.clinicalrecord.risklevel.adapters.out.persistence.mappers;

import com.mindconnect.domain.clinicalrecord.risklevel.model.aggregate.RiskLevel;
import com.mindconnect.domain.clinicalrecord.risklevel.model.valueobject.RiskLevelId;
import com.mindconnect.infrastructure.clinicalrecord.risklevel.adapters.out.persistence.entity.RiskLevelJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class RiskLevelPersistenceMapper {

    public RiskLevel toDomain(RiskLevelJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        RiskLevelId id = new RiskLevelId(entity.getId());
        return RiskLevel.restore(
                id,
                entity.getCode(),
                entity.getName(),
                entity.isActive(),
                entity.getSeverity(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public RiskLevelJpaEntity toJpa(RiskLevel level) {
        if (level == null) {
            return null;
        }
        RiskLevelJpaEntity entity = new RiskLevelJpaEntity();
        entity.setId(level.id().value());
        entity.setCode(level.code());
        entity.setName(level.name());
        entity.setActive(level.active());
        entity.setSeverity(level.severity());
        entity.setCreatedAt(level.createdAt());
        entity.setUpdatedAt(level.updatedAt());
        return entity;
    }
}