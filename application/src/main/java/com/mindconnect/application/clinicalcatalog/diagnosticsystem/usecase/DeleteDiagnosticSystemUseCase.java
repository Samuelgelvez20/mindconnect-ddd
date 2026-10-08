package com.mindconnect.application.clinicalcatalog.diagnosticsystem.usecase;

import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.aggregate.DiagnosticSystem;
import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.valueobject.DiagnosticSystemId;
import com.mindconnect.application.clinicalcatalog.diagnosticsystem.exception.DiagnosticSystemNotFoundApplicationException;
import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.port.repository.DiagnosticSystemRepository;

public class DeleteDiagnosticSystemUseCase {

    private final DiagnosticSystemRepository repository;

    public DeleteDiagnosticSystemUseCase(DiagnosticSystemRepository repository) {
        this.repository = repository;
    }

    public void execute(DiagnosticSystemId id) {
        DiagnosticSystem system = repository.findById(id)
                .orElseThrow(() -> new DiagnosticSystemNotFoundApplicationException(id.value().toString()));

        repository.delete(system);
    }
}