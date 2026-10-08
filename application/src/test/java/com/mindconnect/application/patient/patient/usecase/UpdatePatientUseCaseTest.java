package com.mindconnect.application.patient.patient.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.patient.patient.command.UpdatePatientCommand;
import com.mindconnect.application.patient.patient.dto.PatientResponse;
import com.mindconnect.application.patient.patient.exception.PatientAlreadyExistsApplicationException;
import com.mindconnect.application.patient.patient.exception.PatientNotFoundApplicationException;
import com.mindconnect.domain.patient.patient.model.aggregate.Patient;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;
import com.mindconnect.domain.referencedata.gender.model.valueobject.GenderId;

class UpdatePatientUseCaseTest {

    @Test
    void shouldUpdateExistingPatient() {
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
        UpdatePatientUseCase useCase = new UpdatePatientUseCase(repository);

        PatientResponse response = useCase.execute(
                new UpdatePatientCommand(
                        patient.id(),
                        documentTypeId,
                        "87654321",
                        "Maria", "Luisa", "Gomez", "Lopez",
                        java.time.LocalDate.of(1985, 8, 20),
                        GenderId.generate(), GenderId.generate(),
                        "maria.lopez@example.com", "3109876543", "Carrera 45",
                        false, ProfessionalId.generate(), CityMunicipalityId.generate()));

        assertEquals("Maria", response.firstName());
        assertEquals("Gomez", response.lastName());
        assertEquals("87654321", response.documentNumber());
        assertFalse(response.active());
    }

    @Test
    void shouldAllowKeepingTheSameEmail() {
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
        UpdatePatientUseCase useCase =
                new UpdatePatientUseCase(new FakePatientRepository().with(patient));

        PatientResponse response = useCase.execute(
                new UpdatePatientCommand(
                        patient.id(),
                        documentTypeId,
                        "12345678",
                        "Juan Updated", "Carlos", "Perez", "Gomez",
                        java.time.LocalDate.of(1990, 5, 15),
                        GenderId.generate(), GenderId.generate(),
                        "juan.perez@example.com", "3001234567", "Calle 123",
                        true, ProfessionalId.generate(), cityId));

        assertEquals("Juan Updated", response.firstName());
    }

    @Test
    void shouldAllowKeepingTheSameCompositeDocument() {
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
        UpdatePatientUseCase useCase =
                new UpdatePatientUseCase(new FakePatientRepository().with(patient));

        PatientResponse response = useCase.execute(
                new UpdatePatientCommand(
                        patient.id(),
                        documentTypeId,
                        "12345678",
                        "Juan Updated", "Carlos", "Perez", "Gomez",
                        java.time.LocalDate.of(1990, 5, 15),
                        GenderId.generate(), GenderId.generate(),
                        "juan.perez@example.com", "3001234567", "Calle 123",
                        true, ProfessionalId.generate(), cityId));

        assertEquals("Juan Updated", response.firstName());
    }

    @Test
    void shouldRejectWhenPatientDoesNotExist() {
        UpdatePatientUseCase useCase = new UpdatePatientUseCase(new FakePatientRepository());

        assertThrows(PatientNotFoundApplicationException.class,
                () -> useCase.execute(
                        new UpdatePatientCommand(
                                com.mindconnect.domain.patient.patient.model.valueobject.PatientId.generate(),
                                DocumentTypeId.generate(),
                                "12345678",
                                "Maria", "Luisa", "Gomez", "Lopez",
                                java.time.LocalDate.of(1985, 8, 20),
                                GenderId.generate(), GenderId.generate(),
                                "maria.lopez@example.com", "3109876543", "Carrera 45",
                                true, ProfessionalId.generate(), CityMunicipalityId.generate())));
    }

    @Test
    void shouldRejectEmailUsedByAnotherPatient() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        GenderId biologicalSexId = GenderId.generate();
        GenderId genderIdentityId = GenderId.generate();
        ProfessionalId createdBy = ProfessionalId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        Patient patient1 = Patient.register(
                documentTypeId, "12345678", "Juan", "Carlos", "Perez", "Gomez",
                java.time.LocalDate.of(1990, 5, 15),
                GenderId.generate(), GenderId.generate(),
                "juan.perez@example.com", "3001234567", "Calle 123",
                ProfessionalId.generate(), CityMunicipalityId.generate());
        Patient patient2 = Patient.register(
                DocumentTypeId.generate(), "87654321", "Maria", "Luisa", "Gomez", "Lopez",
                java.time.LocalDate.of(1985, 8, 20),
                GenderId.generate(), GenderId.generate(),
                "maria.lopez@example.com", "3109876543", "Carrera 45",
                ProfessionalId.generate(), CityMunicipalityId.generate());
        UpdatePatientUseCase useCase =
                new UpdatePatientUseCase(new FakePatientRepository().with(patient1, patient2));

        assertThrows(com.mindconnect.application.patient.patient.exception.PatientAlreadyExistsApplicationException.class,
                () -> useCase.execute(
                        new UpdatePatientCommand(
                                patient2.id(),
                                documentTypeId,
                                "87654321",
                                "Maria", "Luisa", "Gomez", "Lopez",
                                java.time.LocalDate.of(1985, 8, 20),
                                GenderId.generate(), GenderId.generate(),
                                "juan.perez@example.com", "3109876543", "Carrera 45",
                                true, ProfessionalId.generate(), cityId)));
    }

    @Test
    void shouldRejectCompositeDocumentUsedByAnotherPatient() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        GenderId biologicalSexId = GenderId.generate();
        GenderId genderIdentityId = GenderId.generate();
        ProfessionalId createdBy = ProfessionalId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        Patient patient1 = Patient.register(
                documentTypeId, "12345678", "Juan", "Carlos", "Perez", "Gomez",
                java.time.LocalDate.of(1990, 5, 15),
                GenderId.generate(), GenderId.generate(),
                "juan.perez@example.com", "3001234567", "Calle 123",
                ProfessionalId.generate(), cityId);
        Patient patient2 = Patient.register(
                DocumentTypeId.generate(), "87654321", "Maria", "Luisa", "Gomez", "Lopez",
                java.time.LocalDate.of(1985, 8, 20),
                GenderId.generate(), GenderId.generate(),
                "maria.lopez@example.com", "3109876543", "Carrera 45",
                ProfessionalId.generate(), CityMunicipalityId.generate());
        UpdatePatientUseCase useCase =
                new UpdatePatientUseCase(new FakePatientRepository().with(patient1, patient2));

        assertThrows(com.mindconnect.application.patient.patient.exception.PatientAlreadyExistsApplicationException.class,
                () -> useCase.execute(
                        new UpdatePatientCommand(
                                patient2.id(),
                                documentTypeId,
                                "12345678",
                                "Maria", "Luisa", "Gomez", "Lopez",
                                java.time.LocalDate.of(1985, 8, 20),
                                GenderId.generate(), GenderId.generate(),
                                "maria.lopez@example.com", "3109876543", "Carrera 45",
                                true, ProfessionalId.generate(), cityId)));
    }
}