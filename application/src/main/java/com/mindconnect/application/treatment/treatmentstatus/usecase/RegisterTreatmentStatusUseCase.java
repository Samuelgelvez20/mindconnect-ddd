package com.mindconnect.application.treatment.treatmentstatus.usecase;

import com.mindconnect.application.treatment.treatmentstatus.dto.TreatmentStatusResponse;
import com.mindconnect.domain.treatment.treatmentstatus.model.aggregate.TreatmentStatus;
import com.mindconnect.domain.treatment.treatmentstatus.model.valueobject.TreatmentStatusId;
import com.mindconnect.application.treatment.treatmentstatus.command.RegisterTreatmentStatusCommand;
import com.mindconnect.application.treatment.treatmentstatus.exception.TreatmentStatusAlreadyExistsApplicationException;
import com.mindconnect.domain.treatment.treatmentstatus.port.repository.TreatmentStatusRepository;

public class RegisterTreatmentStatusUseCase {

    private final TreatmentStatusRepository repository;

    public RegisterTreatmentStatusUseCase(TreatmentStatusRepository repository) {
        this.repository = repository;
    }

    public TreatmentStatusResponse execute(RegisterTreatmentStatusCommand command) {
        TreatmentStatus existing = repository.findById(TreatmentStatusId.generate())
                .filter(t -> t.code().equals(command.code()))
                .orElse(null);

        if (existing != null) {
            throw new TreatmentStatusAlreadyExistsApplicationException("TreatmentStatus with code " + command.code() + " already exists");
        }

        TreatmentStatus status = TreatmentStatus.register(command.code(), command.name());
        repository.save(status);

        return TreatmentStatusResponse.from(status);
    }
}