package com.mindconnect.application.clinicalcatalog.diagnosticsystem.usecase;

import com.mindconnect.application.clinicalcatalog.diagnosticsystem.dto.DiagnosticSystemResponse;
import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.aggregate.DiagnosticSystem;
import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.model.valueobject.DiagnosticSystemId;
import com.mindconnect.application.clinicalcatalog.diagnosticsystem.command.RegisterDiagnosticSystemCommand;
import com.mindconnect.application.clinicalcatalog.diagnosticsystem.exception.DiagnosticSystemAlreadyExistsApplicationException;
import com.mindconnect.domain.clinicalcatalog.diagnosticsystem.port.repository.DiagnosticSystemRepository;

import java.util.Optional;

public class RegisterDiagnosticSystemUseCase {

    private final DiagnosticSystemRepository repository;

    public RegisterDiagnosticSystemUseCase(DiagnosticSystemRepository repository) {
        this.repository = repository;
    }

    public DiagnosticSystemResponse execute(RegisterDiagnosticSystemCommand command) {
        Optional<DiagnosticSystem> existing = repository.findByCode(command.code());

        if (existing.isPresent()) {
            throw new DiagnosticSystemAlreadyExistsApplicationException(command.code());
        }

        DiagnosticSystem system = DiagnosticSystem.register(command.code(), command.name(), command.version());
        repository.save(system);

        return DiagnosticSystemResponse.from(system);
    }
}