package com.mindconnect.application.clinicalcatalog.diagnosticsystem.usecase;

import com.mindconnect.application.clinicalcatalog.diagnosticsystem.dto.DiagnosticSystemResponse;
import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.aggregate.DiagnosticSystem;
import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.valueobject.DiagnosticSystemId;
import com.mindconnect.application.clinicalcatalog.diagnosticsystem.command.UpdateDiagnosticSystemCommand;
import com.mindconnect.application.clinicalcatalog.diagnosticsystem.exception.DiagnosticSystemNotFoundApplicationException;
import com.mindconnect.application.clinicalcatalog.diagnosticsystem.exception.DiagnosticSystemAlreadyExistsApplicationException;
import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.port.repository.DiagnosticSystemRepository;

public class UpdateDiagnosticSystemUseCase {

    private final DiagnosticSystemRepository repository;

    public UpdateDiagnosticSystemUseCase(DiagnosticSystemRepository repository) {
        this.repository = repository;
    }

    public DiagnosticSystemResponse execute(DiagnosticSystemId id, UpdateDiagnosticSystemCommand command) {
        DiagnosticSystem system = repository.findById(id)
                .orElseThrow(() -> new DiagnosticSystemNotFoundApplicationException(id.value().toString()));

        if (repository.existsByCodeAndIdNot(command.code(), id)) {
            throw new DiagnosticSystemAlreadyExistsApplicationException(command.code());
        }

        system.update(command.code(), command.name(), command.version(), command.active());

        repository.save(system);

        return DiagnosticSystemResponse.from(system);
    }
}