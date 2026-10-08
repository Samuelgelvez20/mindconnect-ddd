package com.mindconnect.application.clinicalrecord.risklevel.usecase;

import com.mindconnect.application.clinicalrecord.risklevel.dto.RiskLevelResponse;
import com.mindconnect.application.clinicalrecord.risklevel.exception.RiskLevelNotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.risklevel.model.valueobject.RiskLevelId;
import com.mindconnect.domain.clinicalrecord.risklevel.port.repository.RiskLevelRepository;

public class GetRiskLevelByIdUseCase {

    private final RiskLevelRepository repository;

    public GetRiskLevelByIdUseCase(RiskLevelRepository repository) {
        this.repository = repository;
    }

    public RiskLevelResponse execute(RiskLevelId id) {
        return repository.findById(id)
                .map(RiskLevelResponse::from)
                .orElseThrow(() -> new RiskLevelNotFoundApplicationException(id));
    }
}