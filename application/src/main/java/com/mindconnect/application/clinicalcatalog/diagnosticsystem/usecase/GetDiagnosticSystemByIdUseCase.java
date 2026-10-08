package com.mindconnect.application.clinicalcatalog.diagnosticsystem.usecase;

import com.mindconnect.application.clinicalcatalog.diagnosticsystem.dto.DiagnosticSystemResponse;
import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.aggregate.DiagnosticSystem;
import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.valueobject.DiagnosticSystemId;
import com.mindconnect.application.clinicalcatalog.diagnosticsystem.exception.DiagnosticSystemNotFoundApplicationException;
import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.port.repository.DiagnosticSystemRepository;

public class GetDiagnosticSystemByIdUseCase {

    private final DiagnosticSystemRepository repository;

    public GetDiagnosticSystemByIdUseCase(DiagnosticSystemRepository repository) {
        this.repository = repository;
    }

    public DiagnosticSystemResponse execute(DiagnosticSystemId id) {
        DiagnosticSystem system = repository.findById(id)
                .orElseThrow(() -> new DiagnosticSystemNotFoundApplicationException(id.value().toString()));

        return DiagnosticSystemResponse.from(system);
    }
}