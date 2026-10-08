package com.mindconnect.domain.clinicalrecord.risklevel.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.clinicalrecord.risklevel.model.aggregate.RiskLevel;
import com.mindconnect.domain.clinicalrecord.risklevel.model.valueobject.RiskLevelId;

public interface RiskLevelRepository {

    RiskLevel save(RiskLevel riskLevel);

    Optional<RiskLevel> findById(RiskLevelId id);

    List<RiskLevel> findAll();

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, RiskLevelId id);

    void delete(RiskLevel riskLevel);
}