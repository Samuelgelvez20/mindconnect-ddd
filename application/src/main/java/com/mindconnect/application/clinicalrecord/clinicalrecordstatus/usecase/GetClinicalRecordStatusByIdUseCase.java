package com.mindconnect.application.clinicalrecord.clinicalrecordstatus.usecase;

import com.mindconnect.application.clinicalrecord.clinicalrecordstatus.dto.ClinicalRecordStatusResponse;
import com.mindconnect.application.clinicalrecord.clinicalrecordstatus.exception.ClinicalRecordStatusNotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;
import com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.port.repository.ClinicalRecordStatusRepository;

public class GetClinicalRecordStatusByIdUseCase {

    private final ClinicalRecordStatusRepository repository;

    public GetClinicalRecordStatusByIdUseCase(ClinicalRecordStatusRepository repository) {
        this.repository = repository;
    }

    public ClinicalRecordStatusResponse execute(ClinicalRecordStatusId id) {
        return repository.findById(id)
                .map(ClinicalRecordStatusResponse::from)
                .orElseThrow(() -> new ClinicalRecordStatusNotFoundApplicationException(id));
    }
}