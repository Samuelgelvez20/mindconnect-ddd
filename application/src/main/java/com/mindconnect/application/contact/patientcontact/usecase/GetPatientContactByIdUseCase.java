package com.mindconnect.application.contact.patientcontact.usecase;

import com.mindconnect.application.contact.patientcontact.dto.PatientContactResponse;
import com.mindconnect.application.contact.patientcontact.exception.PatientContactNotFoundApplicationException;
import com.mindconnect.domain.contact.patientcontact.model.valueobject.PatientContactId;
import com.mindconnect.domain.contact.patientcontact.port.repository.PatientContactRepository;

public class GetPatientContactByIdUseCase {

    private final PatientContactRepository repository;

    public GetPatientContactByIdUseCase(PatientContactRepository repository) {
        this.repository = repository;
    }

    public PatientContactResponse execute(PatientContactId id) {
        return repository.findById(id)
                .map(PatientContactResponse::from)
                .orElseThrow(() -> new PatientContactNotFoundApplicationException(id));
    }
}