package com.mindconnect.application.clinicalrecord.clinicalrecord.usecase;

import com.mindconnect.application.clinicalrecord.clinicalrecord.dto.ClinicalRecordResponse;
import com.mindconnect.application.clinicalrecord.clinicalrecord.exception.ClinicalRecordNotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.clinicalrecord.model.valueobject.ClinicalRecordId;
import com.mindconnect.domain.clinicalrecord.clinicalrecord.port.repository.ClinicalRecordRepository;

public class GetClinicalRecordByIdUseCase {

    private final ClinicalRecordRepository repository;

    public GetClinicalRecordByIdUseCase(ClinicalRecordRepository repository) {
        this.repository = repository;
    }

    public ClinicalRecordResponse execute(ClinicalRecordId id) {
        return repository.findById(id)
                .map(ClinicalRecordResponse::from)
                .orElseThrow(() -> new ClinicalRecordNotFoundApplicationException(id));
    }
}