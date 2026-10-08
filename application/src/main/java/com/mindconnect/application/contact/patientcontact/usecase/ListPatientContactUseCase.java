package com.mindconnect.application.contact.patientcontact.usecase;

import java.util.List;

import com.mindconnect.application.contact.patientcontact.dto.PatientContactResponse;
import com.mindconnect.domain.contact.patientcontact.port.repository.PatientContactRepository;

public class ListPatientContactUseCase {

    private final PatientContactRepository repository;

    public ListPatientContactUseCase(PatientContactRepository repository) {
        this.repository = repository;
    }

    public List<PatientContactResponse> execute() {
        return repository.findAll()
                .stream()
                .map(PatientContactResponse::from)
                .toList();
    }
}