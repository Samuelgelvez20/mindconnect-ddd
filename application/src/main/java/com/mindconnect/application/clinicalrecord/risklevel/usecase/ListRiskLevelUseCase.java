package com.mindconnect.application.clinicalrecord.risklevel.usecase;

import java.util.List;

import com.mindconnect.application.clinicalrecord.risklevel.dto.RiskLevelResponse;
import com.mindconnect.domain.clinicalrecord.risklevel.port.repository.RiskLevelRepository;

public class ListRiskLevelUseCase {

    private final RiskLevelRepository repository;

    public ListRiskLevelUseCase(RiskLevelRepository repository) {
        this.repository = repository;
    }

    public List<RiskLevelResponse> execute() {
        return repository.findAll()
                .stream()
                .map(RiskLevelResponse::from)
                .toList();
    }
}