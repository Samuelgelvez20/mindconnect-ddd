package com.mindconnect.application.clinicalrecord.clinicalrecord.usecase;

import com.mindconnect.application.clinicalrecord.clinicalrecord.command.RegisterClinicalRecordCommand;
import com.mindconnect.application.clinicalrecord.clinicalrecord.dto.ClinicalRecordResponse;
import com.mindconnect.application.clinicalrecord.clinicalrecord.exception.ClinicalRecordNotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.clinicalrecord.model.aggregate.ClinicalRecord;
import com.mindconnect.domain.clinicalrecord.clinicalrecord.port.repository.ClinicalRecordRepository;

public class RegisterClinicalRecordUseCase {

    private final ClinicalRecordRepository repository;

    public RegisterClinicalRecordUseCase(ClinicalRecordRepository repository) {
        this.repository = repository;
    }

    public ClinicalRecordResponse execute(RegisterClinicalRecordCommand command) {

        ClinicalRecord record = ClinicalRecord.register(
                command.patientId(),
                command.recordNumber(),
                command.statusId(),
                command.createdBy());

        return ClinicalRecordResponse.from(repository.save(record));
    }
}