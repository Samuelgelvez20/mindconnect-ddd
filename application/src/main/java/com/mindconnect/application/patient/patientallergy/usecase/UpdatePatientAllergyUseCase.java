package com.mindconnect.application.patient.patientallergy.usecase;

import com.mindconnect.application.patient.patientallergy.command.UpdatePatientAllergyCommand;
import com.mindconnect.application.patient.patientallergy.dto.PatientAllergyResponse;
import com.mindconnect.application.patient.patientallergy.exception.PatientAllergyNotFoundApplicationException;
import com.mindconnect.domain.patient.patientallergy.port.repository.PatientAllergyRepository;

public class UpdatePatientAllergyUseCase {

    private final PatientAllergyRepository patientAllergyRepository;

    public UpdatePatientAllergyUseCase(PatientAllergyRepository patientAllergyRepository) {
        this.patientAllergyRepository = patientAllergyRepository;
    }

    public PatientAllergyResponse execute(UpdatePatientAllergyCommand command) {

        var patientAllergy = patientAllergyRepository.findById(command.id())
                .orElseThrow(() -> new PatientAllergyNotFoundApplicationException(command.id()));

        patientAllergy.update(
                command.substance(),
                command.reaction(),
                command.severity(),
                command.active());

        return PatientAllergyResponse.from(patientAllergyRepository.save(patientAllergy));
    }
}