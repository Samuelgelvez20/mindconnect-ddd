package com.mindconnect.application.clinicalrecord.risklevel.usecase;

import com.mindconnect.application.clinicalrecord.risklevel.command.RegisterRiskLevelCommand;
import com.mindconnect.application.clinicalrecord.risklevel.dto.RiskLevelResponse;
import com.mindconnect.application.clinicalrecord.risklevel.exception.RiskLevelAlreadyExistsApplicationException;
import com.mindconnect.domain.clinicalrecord.risklevel.model.aggregate.RiskLevel;
import com.mindconnect.domain.clinicalrecord.risklevel.port.repository.RiskLevelRepository;

public class RegisterRiskLevelUseCase {

    private final RiskLevelRepository repository;

    public RegisterRiskLevelUseCase(RiskLevelRepository repository) {
        this.repository = repository;
    }

    public RiskLevelResponse execute(RegisterRiskLevelCommand command) {

        RiskLevel level = RiskLevel.register(command.code(), command.name(), command.severity());

        if (repository.existsByCode(level.code())) {
            throw new RiskLevelAlreadyExistsApplicationException(level.code());
        }

        return RiskLevelResponse.from(repository.save(level));
    }
}