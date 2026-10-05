package com.mindconnect.application.patient.patientallergy.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.patient.patientallergy.command.UpdatePatientAllergyCommand;
import com.mindconnect.application.patient.patientallergy.dto.PatientAllergyResponse;
import com.mindconnect.application.patient.patientallergy.exception.PatientAllergyNotFoundApplicationException;
import com.mindconnect.domain.patient.patientallergy.model.aggregate.PatientAllergy;
import com.mindconnect.domain.patient.patientallergy.model.valueobject.PatientAllergyId;
import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

class UpdatePatientAllergyUseCaseTest {

    @Test
    void shouldUpdateExistingPatientAllergy() {
        PatientId patientId = PatientId.generate();
        ProfessionalId recordedBy = ProfessionalId.generate();

        PatientAllergy patientAllergy = PatientAllergy.register(
                PatientId.generate(), "Penicillin", "Rash", "Mild", ProfessionalId.generate());
        FakePatientAllergyRepository repository = new FakePatientAllergyRepository().with(patientAllergy);
        UpdatePatientAllergyUseCase useCase = new UpdatePatientAllergyUseCase(repository);

        PatientAllergyResponse response = useCase.execute(
                new UpdatePatientAllergyCommand(patientAllergy.id(), "Amoxicillin",
                        "Hives", "Moderate", true));

        assertEquals("Amoxicillin", response.substance());
        assertEquals("Hives", response.reaction());
        assertEquals("Moderate", response.severity());
        assertTrue(response.active());
    }

    @Test
    void shouldRejectWhenPatientAllergyDoesNotExist() {
        UpdatePatientAllergyUseCase useCase = new UpdatePatientAllergyUseCase(new FakePatientAllergyRepository());

        assertThrows(PatientAllergyNotFoundApplicationException.class,
                () -> useCase.execute(
                        new UpdatePatientAllergyCommand(PatientAllergyId.generate(),
                                "Amoxicillin", "Hives", "Moderate", true)));
    }
}