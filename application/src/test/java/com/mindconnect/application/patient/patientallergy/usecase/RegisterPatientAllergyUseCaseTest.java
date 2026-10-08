package com.mindconnect.application.patient.patientallergy.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.patient.patientallergy.command.RegisterPatientAllergyCommand;
import com.mindconnect.application.patient.patientallergy.dto.PatientAllergyResponse;
import com.mindconnect.domain.patient.patientallergy.exception.InvalidPatientAllergyException;
import com.mindconnect.domain.patient.patientallergy.model.aggregate.PatientAllergy;
import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

class RegisterPatientAllergyUseCaseTest {

    @Test
    void shouldRegisterAndPersistPatientAllergy() {
        PatientId patientId = PatientId.generate();
        ProfessionalId recordedBy = ProfessionalId.generate();

        FakePatientAllergyRepository repository = new FakePatientAllergyRepository();
        RegisterPatientAllergyUseCase useCase = new RegisterPatientAllergyUseCase(repository);

        PatientAllergyResponse response = useCase.execute(
                new RegisterPatientAllergyCommand(patientId, "Penicillin", "Rash", "Mild",
                        recordedBy));

        assertNotNull(response.id());
        assertEquals(patientId.value(), response.patientId());
        assertEquals("Penicillin", response.substance());
        assertEquals("Rash", response.reaction());
        assertEquals("Mild", response.severity());
        assertFalse(response.isValid());
        assertNull(response.resolutionNumber());
        assertEquals(1, repository.size());
    }

    @Test
    void shouldPropagateDomainValidation() {
        RegisterPatientAllergyUseCase useCase = new RegisterPatientAllergyUseCase(new FakePatientAllergyRepository());

        assertThrows(InvalidPatientAllergyException.class,
                () -> useCase.execute(new RegisterPatientAllergyCommand(
                        PatientId.generate(), "  ", "Rash", "Mild", ProfessionalId.generate())));
    }
}