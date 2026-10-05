package com.mindconnect.application.patient.patientallergy.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.patient.patientallergy.exception.PatientAllergyNotFoundApplicationException;
import com.mindconnect.domain.patient.patientallergy.event.PatientAllergyDeletedEvent;
import com.mindconnect.domain.patient.patientallergy.model.aggregate.PatientAllergy;
import com.mindconnect.domain.patient.patientallergy.model.valueobject.PatientAllergyId;
import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

class DeletePatientAllergyUseCaseTest {

    @Test
    void shouldDeleteExistingPatientAllergyAndRecordEvent() {
        PatientId patientId = PatientId.generate();
        ProfessionalId recordedBy = ProfessionalId.generate();

        PatientAllergy patientAllergy = PatientAllergy.register(
                PatientId.generate(), "Penicillin", "Rash", "Mild", ProfessionalId.generate());
        FakePatientAllergyRepository repository = new FakePatientAllergyRepository().with(patientAllergy);
        DeletePatientAllergyUseCase useCase = new DeletePatientAllergyUseCase(repository);

        useCase.execute(patientAllergy.id());

        assertEquals(1, repository.deleted().size());
        assertSame(patientAllergy, repository.deleted().getFirst());
        assertEquals(0, repository.size());
        assertInstanceOf(PatientAllergyDeletedEvent.class, patientAllergy.domainEvents().getLast());
    }

    @Test
    void shouldRejectWhenPatientAllergyDoesNotExist() {
        FakePatientAllergyRepository repository = new FakePatientAllergyRepository();
        DeletePatientAllergyUseCase useCase = new DeletePatientAllergyUseCase(repository);

        assertThrows(PatientAllergyNotFoundApplicationException.class,
                () -> useCase.execute(PatientAllergyId.generate()));
        assertTrue(repository.deleted().isEmpty());
    }
}