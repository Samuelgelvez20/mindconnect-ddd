package com.mindconnect.application.contact.patientcontact.usecase;

import com.mindconnect.application.contact.patientcontact.exception.PatientContactNotFoundApplicationException;
import com.mindconnect.domain.contact.patientcontact.model.valueobject.PatientContactId;
import com.mindconnect.domain.contact.patientcontact.port.repository.PatientContactRepository;

public class DeletePatientContactUseCase {

    private final PatientContactRepository repository;

    public DeletePatientContactUseCase(PatientContactRepository repository) {
        this.repository = repository;
    }

    public void execute(PatientContactId id) {

        var patientContact = repository.findById(id)
                .orElseThrow(() -> new PatientContactNotFoundApplicationException(id));

        patientContact.delete();
        repository.delete(patientContact);
    }
}