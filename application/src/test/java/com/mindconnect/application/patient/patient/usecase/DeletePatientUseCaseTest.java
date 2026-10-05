package com.mindconnect.application.patient.patient.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.patient.patient.exception.PatientNotFoundApplicationException;
import com.mindconnect.domain.patient.patient.event.PatientDeletedEvent;
import com.mindconnect.domain.patient.patient.model.aggregate.Patient;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;
import com.mindconnect.domain.referencedata.gender.model.valueobject.GenderId;

class DeletePatientUseCaseTest {

    @Test
    void shouldDeleteExistingPatientAndRecordEvent() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        GenderId biologicalSexId = GenderId.generate();
        GenderId genderIdentityId = GenderId.generate();
        ProfessionalId createdBy = ProfessionalId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        Patient patient = Patient.register(
                documentTypeId, "12345678", "Juan", "Carlos", "Perez", "Gomez",
                java.time.LocalDate.of(1990, 5, 15),
                GenderId.generate(), GenderId.generate(),
                "juan.perez@example.com", "3001234567", "Calle 123",
                ProfessionalId.generate(), cityId);
        FakePatientRepository repository = new FakePatientRepository().with(patient);
        DeletePatientUseCase useCase = new DeletePatientUseCase(repository);

        useCase.execute(patient.id());

        assertEquals(1, repository.deleted().size());
        assertSame(patient, repository.deleted().getFirst());
        assertEquals(0, repository.size());
        assertInstanceOf(PatientDeletedEvent.class, patient.domainEvents().getLast());
    }

    @Test
    void shouldRejectWhenPatientDoesNotExist() {
        FakePatientRepository repository = new FakePatientRepository();
        DeletePatientUseCase useCase = new DeletePatientUseCase(repository);

        assertThrows(PatientNotFoundApplicationException.class,
                () -> useCase.execute(com.mindconnect.domain.patient.patient.model.valueobject.PatientId.generate()));
        assertTrue(repository.deleted().isEmpty());
    }
}