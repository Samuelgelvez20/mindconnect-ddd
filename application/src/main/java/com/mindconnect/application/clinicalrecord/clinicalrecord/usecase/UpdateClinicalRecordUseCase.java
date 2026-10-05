package com.mindconnect.application.clinicalrecord.clinicalrecord.usecase;

import com.mindconnect.application.clinicalrecord.clinicalrecord.command.UpdateClinicalRecordCommand;
import com.mindconnect.application.clinicalrecord.clinicalrecord.dto.ClinicalRecordResponse;
import com.mindconnect.application.clinicalrecord.clinicalrecord.exception.ClinicalRecordNotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.clinicalrecord.model.aggregate.ClinicalRecord;
import com.mindconnect.domain.clinicalrecord.clinicalrecord.port.repository.ClinicalRecordRepository;

public class UpdateClinicalRecordUseCase {

    private final ClinicalRecordRepository repository;

    public UpdateClinicalRecordUseCase(ClinicalRecordRepository repository) {
        this.repository = repository;
    }

    public ClinicalRecordResponse execute(UpdateClinicalRecordCommand command) {

        var record = repository.findById(command.id())
                .orElseThrow(() -> new ClinicalRecordNotFoundApplicationException(command.id()));

        record.update(
                command.patientId(),
                command.recordNumber(),
                command.openedAt(),
                command.closedAt(),
                command.statusId());

        return ClinicalRecordResponse.from(repository.save(record));
    }
}