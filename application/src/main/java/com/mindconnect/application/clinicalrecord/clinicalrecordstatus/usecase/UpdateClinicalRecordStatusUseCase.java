package com.mindconnect.application.clinicalrecord.clinicalrecordstatus.usecase;

import com.mindconnect.application.clinicalrecord.clinicalrecordstatus.command.UpdateClinicalRecordStatusCommand;
import com.mindconnect.application.clinicalrecord.clinicalrecordstatus.dto.ClinicalRecordStatusResponse;
import com.mindconnect.application.clinicalrecord.clinicalrecordstatus.exception.ClinicalRecordStatusAlreadyExistsApplicationException;
import com.mindconnect.application.clinicalrecord.clinicalrecordstatus.exception.ClinicalRecordStatusNotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.model.aggregate.ClinicalRecordStatus;
import com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.port.repository.ClinicalRecordStatusRepository;

public class UpdateClinicalRecordStatusUseCase {

    private final ClinicalRecordStatusRepository repository;

    public UpdateClinicalRecordStatusUseCase(ClinicalRecordStatusRepository repository) {
        this.repository = repository;
    }

    public ClinicalRecordStatusResponse execute(UpdateClinicalRecordStatusCommand command) {

        var status = repository.findById(command.id())
                .orElseThrow(() -> new ClinicalRecordStatusNotFoundApplicationException(command.id()));

        status.update(command.code(), command.name());

        if (repository.existsByCodeAndIdNot(status.code(), status.id())) {
            throw new ClinicalRecordStatusAlreadyExistsApplicationException(status.code());
        }
        if (repository.existsByNameAndIdNot(status.name(), status.id())) {
            throw new ClinicalRecordStatusAlreadyExistsApplicationException(status.code(), status.name());
        }

        return ClinicalRecordStatusResponse.from(repository.save(status));
    }
}