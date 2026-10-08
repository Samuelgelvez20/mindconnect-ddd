package com.mindconnect.application.clinicalrecord.clinicalrecordstatus.usecase;

import com.mindconnect.application.clinicalrecord.clinicalrecordstatus.exception.ClinicalRecordStatusNotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;
import com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.port.repository.ClinicalRecordStatusRepository;

public class DeleteClinicalRecordStatusUseCase {

    private final ClinicalRecordStatusRepository repository;

    public DeleteClinicalRecordStatusUseCase(ClinicalRecordStatusRepository repository) {
        this.repository = repository;
    }

    public void execute(ClinicalRecordStatusId id) {

        var status = repository.findById(id)
                .orElseThrow(() -> new ClinicalRecordStatusNotFoundApplicationException(id));

        status.delete();
        repository.delete(status);
    }
}