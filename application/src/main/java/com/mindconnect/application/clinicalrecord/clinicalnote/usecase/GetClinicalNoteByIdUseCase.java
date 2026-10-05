package com.mindconnect.application.clinicalrecord.clinicalnote.usecase;

import com.mindconnect.application.clinicalrecord.clinicalnote.dto.ClinicalNoteResponse;
import com.mindconnect.application.clinicalrecord.clinicalnote.exception.ClinicalNoteNotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.clinicalnote.model.valueobject.ClinicalNoteId;
import com.mindconnect.domain.clinicalrecord.clinicalnote.port.repository.ClinicalNoteRepository;

public class GetClinicalNoteByIdUseCase {

    private final ClinicalNoteRepository repository;

    public GetClinicalNoteByIdUseCase(ClinicalNoteRepository repository) {
        this.repository = repository;
    }

    public ClinicalNoteResponse execute(ClinicalNoteId id) {
        return repository.findById(id)
                .map(ClinicalNoteResponse::from)
                .orElseThrow(() -> new ClinicalNoteNotFoundApplicationException(id));
    }
}