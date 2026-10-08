package com.mindconnect.application.clinicalrecord.risklevel.usecase;

import com.mindconnect.application.clinicalrecord.risklevel.exception.RiskLevelNotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.risklevel.model.valueobject.RiskLevelId;
import com.mindconnect.domain.clinicalrecord.risklevel.port.repository.RiskLevelRepository;

public class DeleteRiskLevelUseCase {

    private final RiskLevelRepository repository;

    public DeleteRiskLevelUseCase(RiskLevelRepository repository) {
        this.repository = repository;
    }

    public void execute(RiskLevelId id) {

        var level = repository.findById(id)
                .orElseThrow(() -> new RiskLevelNotFoundApplicationException(id));

        level.delete();
        repository.delete(level);
    }
}