package com.mindconnect.application.patient.patient.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.patient.patient.command.RegisterPatientCommand;
import com.mindconnect.application.patient.patient.dto.PatientResponse;
import com.mindconnect.application.patient.patient.exception.PatientAlreadyExistsApplicationException;
import com.mindconnect.domain.patient.patient.exception.InvalidPatientException;
import com.mindconnect.domain.patient.patient.model.aggregate.Patient;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;
import com.mindconnect.domain.referencedata.gender.model.valueobject.GenderId;

class RegisterPatientUseCaseTest {

    @Test
    void shouldRegisterAndPersistPatient() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        GenderId biologicalSexId = GenderId.generate();
        GenderId genderIdentityId = GenderId.generate();
        ProfessionalId createdBy = ProfessionalId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        FakePatientRepository repository = new FakePatientRepository();
        RegisterPatientUseCase useCase = new RegisterPatientUseCase(repository);

        PatientResponse response = useCase.execute(
                new RegisterPatientCommand(
                        documentTypeId, "12345678", "Juan", "Carlos", "Perez", "Gomez",
                        java.time.LocalDate.of(1990, 5, 15),
                        GenderId.generate(), GenderId.generate(),
                        "juan.perez@example.com", "3001234567", "Calle 123",
                        ProfessionalId.generate(), cityId));

        assertNotNull(response.id());
        assertEquals("12345678", response.documentNumber());
        assertEquals("Juan", response.firstName());
        assertEquals("Perez", response.lastName());
        assertEquals("juan.perez@example.com", response.email());
        assertTrue(response.active());
        assertEquals(1, repository.size());
    }

    @Test
    void shouldRejectDuplicatedEmail() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        GenderId biologicalSexId = GenderId.generate();
        GenderId genderIdentityId = GenderId.generate();
        ProfessionalId createdBy = ProfessionalId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        FakePatientRepository repository =
                new FakePatientRepository().with(
                        Patient.register(
                                DocumentTypeId.generate(), "12345678", "Juan", "Carlos", "Perez", "Gomez",
                                java.time.LocalDate.of(1990, 5, 15),
                                GenderId.generate(), GenderId.generate(),
                                "juan.perez@example.com", "3001234567", "Calle 123",
                                ProfessionalId.generate(), CityMunicipalityId.generate()));
        RegisterPatientUseCase useCase = new RegisterPatientUseCase(repository);

        assertThrows(PatientAlreadyExistsApplicationException.class,
                () -> useCase.execute(new RegisterPatientCommand(
                        documentTypeId, "87654321", "Maria", "Luisa", "Gomez", "Lopez",
                        java.time.LocalDate.of(1985, 8, 20),
                        GenderId.generate(), GenderId.generate(),
                        "  juan.perez@example.com  ", "3109876543", "Carrera 45",
                        ProfessionalId.generate(), cityId)));
        assertEquals(1, repository.size());
    }

    @Test
    void shouldRejectDuplicatedCompositeDocument() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        GenderId biologicalSexId = GenderId.generate();
        GenderId genderIdentityId = GenderId.generate();
        ProfessionalId createdBy = ProfessionalId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        FakePatientRepository repository =
                new FakePatientRepository().with(
                        Patient.register(
                                documentTypeId, "12345678", "Juan", "Carlos", "Perez", "Gomez",
                                java.time.LocalDate.of(1990, 5, 15),
                                GenderId.generate(), GenderId.generate(),
                                "juan.perez@example.com", "3001234567", "Calle 123",
                                ProfessionalId.generate(), CityMunicipalityId.generate()));
        RegisterPatientUseCase useCase = new RegisterPatientUseCase(repository);

        assertThrows(PatientAlreadyExistsApplicationException.class,
                () -> useCase.execute(new RegisterPatientCommand(
                        documentTypeId, " 12345678 ", "Maria", "Luisa", "Gomez", "Lopez",
                        java.time.LocalDate.of(1985, 8, 20),
                        GenderId.generate(), GenderId.generate(),
                        "maria.lopez@example.com", "3109876543", "Carrera 45",
                        ProfessionalId.generate(), cityId)));
        assertEquals(1, repository.size());
    }

    @Test
    void shouldPropagateDomainValidation() {
        RegisterPatientUseCase useCase = new RegisterPatientUseCase(new FakePatientRepository());

        assertThrows(InvalidPatientException.class,
                () -> useCase.execute(new RegisterPatientCommand(
                        DocumentTypeId.generate(), "  ", "Juan", null, "Perez", null,
                        java.time.LocalDate.of(1990, 5, 15),
                        GenderId.generate(), GenderId.generate(),
                        "juan@example.com", null, null,
                        ProfessionalId.generate(), CityMunicipalityId.generate())));
    }
}