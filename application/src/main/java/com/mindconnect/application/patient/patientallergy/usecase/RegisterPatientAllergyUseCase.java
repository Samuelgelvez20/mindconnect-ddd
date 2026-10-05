package com.mindconnect.application.patient.patientallergy.usecase;

import com.mindconnect.application.patient.patientallergy.command.RegisterPatientAllergyCommand;
import com.mindconnect.application.patient.patientallergy.dto.PatientAllergyResponse;
import com.mindconnect.domain.patient.patientallergy.model.aggregate.PatientAllergy;
import com.mindconnect.domain.patient.patientallergy.port.repository.PatientAllergyRepository;

public class RegisterPatientAllergyUseCase {

    private final PatientAllergyRepository patientAllergyRepository;

    public RegisterPatientAllergyUseCase(PatientAllergyRepository patientAllergyRepository) {
        this.patientAllergyRepository = patientAllergyRepository;
    }

    public PatientAllergyResponse execute(RegisterPatientAllergyCommand command) {

        PatientAllergy patientAllergy = PatientAllergy.register(
                command.patientId(),
                command.substance(),
                command.reaction(),
                command.severity(),
                command.recordedBy());

        return PatientAllergyResponse.from(patientAllergyRepository.save(patientAllergy));
    }
}