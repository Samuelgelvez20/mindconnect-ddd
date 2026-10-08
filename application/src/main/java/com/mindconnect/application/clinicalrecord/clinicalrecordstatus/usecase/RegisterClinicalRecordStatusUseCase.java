package com.mindconnect.application.clinicalrecord.clinicalrecordstatus.usecase;

import com.mindconnect.application.clinicalrecord.clinicalrecordstatus.command.RegisterClinicalRecordStatusCommand;
import com.mindconnect.application.clinicalrecord.clinicalrecordstatus.dto.ClinicalRecordStatusResponse;
import com.mindconnect.application.clinicalrecord.clinicalrecordstatus.exception.ClinicalRecordStatusAlreadyExistsApplicationException;
import com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.model.aggregate.ClinicalRecordStatus;
import com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.port.repository.ClinicalRecordStatusRepository;

public class RegisterClinicalRecordStatusUseCase {

    private final ClinicalRecordStatusRepository repository;

    public RegisterClinicalRecordStatusUseCase(ClinicalRecordStatusRepository repository) {
        this.repository = repository;
    }

    public ClinicalRecordStatusResponse execute(RegisterClinicalRecordStatusCommand command) {

        ClinicalRecordStatus status = ClinicalRecordStatus.register(command.code(), command.name());

        if (repository.existsByCode(status.code())) {
            throw new ClinicalRecordStatusAlreadyExistsApplicationException(status.code());
        }
        if (repository.existsByName(status.name())) {
            throw new ClinicalRecordStatusAlreadyExistsApplicationException(status.code(), status.name());
        }

        return ClinicalRecordStatusResponse.from(repository.save(status));
    }
}