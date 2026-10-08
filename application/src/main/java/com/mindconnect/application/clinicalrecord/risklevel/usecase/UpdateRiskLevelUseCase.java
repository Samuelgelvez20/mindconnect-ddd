package com.mindconnect.application.clinicalrecord.risklevel.usecase;

import com.mindconnect.application.clinicalrecord.risklevel.command.UpdateRiskLevelCommand;
import com.mindconnect.application.clinicalrecord.risklevel.dto.RiskLevelResponse;
import com.mindconnect.application.clinicalrecord.risklevel.exception.RiskLevelAlreadyExistsApplicationException;
import com.mindconnect.application.clinicalrecord.risklevel.exception.RiskLevelNotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.risklevel.model.aggregate.RiskLevel;
import com.mindconnect.domain.clinicalrecord.risklevel.port.repository.RiskLevelRepository;

public class UpdateRiskLevelUseCase {

    private final RiskLevelRepository repository;

    public UpdateRiskLevelUseCase(RiskLevelRepository repository) {
        this.repository = repository;
    }

    public RiskLevelResponse execute(UpdateRiskLevelCommand command) {

        var level = repository.findById(command.id())
                .orElseThrow(() -> new RiskLevelNotFoundApplicationException(command.id()));

        level.update(command.code(), command.name(), command.active(), command.severity());

        if (repository.existsByCodeAndIdNot(level.code(), level.id())) {
            throw new RiskLevelAlreadyExistsApplicationException(level.code());
        }

        return RiskLevelResponse.from(repository.save(level));
    }
}