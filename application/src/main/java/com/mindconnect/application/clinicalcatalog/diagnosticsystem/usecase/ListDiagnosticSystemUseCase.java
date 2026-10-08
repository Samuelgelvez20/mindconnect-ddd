package com.mindconnect.application.clinicalcatalog.diagnosticsystem.usecase;

import com.mindconnect.application.clinicalcatalog.diagnosticsystem.dto.DiagnosticSystemResponse;
import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.aggregate.DiagnosticSystem;
import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.port.repository.DiagnosticSystemRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ListDiagnosticSystemUseCase {

    private final DiagnosticSystemRepository repository;

    public ListDiagnosticSystemUseCase(DiagnosticSystemRepository repository) {
        this.repository = repository;
    }

    public List<DiagnosticSystemResponse> execute() {
        return repository.findAll().stream()
                .map(DiagnosticSystemResponse::from)
                .collect(Collectors.toList());
    }
}