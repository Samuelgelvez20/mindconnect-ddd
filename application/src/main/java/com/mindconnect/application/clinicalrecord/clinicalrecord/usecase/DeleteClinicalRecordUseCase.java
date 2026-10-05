package com.mindconnect.application.clinicalrecord.clinicalrecord.usecase;

import com.mindconnect.application.clinicalrecord.clinicalrecord.exception.ClinicalRecordNotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.clinicalrecord.model.valueobject.ClinicalRecordId;
import com.mindconnect.domain.clinicalrecord.clinicalrecord.port.repository.ClinicalRecordRepository;

public class DeleteClinicalRecordUseCase {

    private final ClinicalRecordRepository repository;

    public DeleteClinicalRecordUseCase(ClinicalRecordRepository repository) {
        this.repository = repository;
    }

    public void execute(ClinicalRecordId id) {

        var record = repository.findById(id)
                .orElseThrow(() -> new ClinicalRecordNotFoundApplicationException(id));

        record.delete();
        repository.delete(record);
    }
}