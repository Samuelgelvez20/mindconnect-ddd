package com.mindconnect.application.contact.patientcontact.usecase;

import com.mindconnect.application.contact.patientcontact.command.RegisterPatientContactCommand;
import com.mindconnect.application.contact.patientcontact.dto.PatientContactResponse;
import com.mindconnect.domain.contact.patientcontact.model.aggregate.PatientContact;
import com.mindconnect.domain.contact.patientcontact.port.repository.PatientContactRepository;

public class RegisterPatientContactUseCase {

    private final PatientContactRepository repository;

    public RegisterPatientContactUseCase(PatientContactRepository repository) {
        this.repository = repository;
    }

    public PatientContactResponse execute(RegisterPatientContactCommand command) {

        PatientContact patientContact = PatientContact.register(
                command.contactId(),
                command.patientId(),
                command.relationshipTypeId());

        return PatientContactResponse.from(repository.save(patientContact));
    }
}