package com.mindconnect.application.contact.patientcontact.usecase;

import com.mindconnect.application.contact.patientcontact.command.UpdatePatientContactCommand;
import com.mindconnect.application.contact.patientcontact.dto.PatientContactResponse;
import com.mindconnect.application.contact.patientcontact.exception.PatientContactNotFoundApplicationException;
import com.mindconnect.domain.contact.patientcontact.model.aggregate.PatientContact;
import com.mindconnect.domain.contact.patientcontact.port.repository.PatientContactRepository;

public class UpdatePatientContactUseCase {

    private final PatientContactRepository repository;

    public UpdatePatientContactUseCase(PatientContactRepository repository) {
        this.repository = repository;
    }

    public PatientContactResponse execute(UpdatePatientContactCommand command) {

        var patientContact = repository.findById(command.id())
                .orElseThrow(() -> new PatientContactNotFoundApplicationException(command.id()));

        patientContact.update(
                command.isPrimaryContact(),
                command.isEmergencyContact(),
                command.relationshipTypeId());

        return PatientContactResponse.from(repository.save(patientContact));
    }
}