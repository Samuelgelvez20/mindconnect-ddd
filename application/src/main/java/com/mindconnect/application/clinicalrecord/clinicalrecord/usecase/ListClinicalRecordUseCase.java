package com.mindconnect.application.clinicalrecord.clinicalrecord.usecase;

import java.util.List;

import com.mindconnect.application.clinicalrecord.clinicalrecord.dto.ClinicalRecordResponse;
import com.mindconnect.domain.clinicalrecord.clinicalrecord.port.repository.ClinicalRecordRepository;

public class ListClinicalRecordUseCase {

    private final ClinicalRecordRepository repository;

    public ListClinicalRecordUseCase(ClinicalRecordRepository repository) {
        this.repository = repository;
    }

    public List<ClinicalRecordResponse> execute() {
        return repository.findAll()
                .stream()
                .map(ClinicalRecordResponse::from)
                .toList();
    }
}