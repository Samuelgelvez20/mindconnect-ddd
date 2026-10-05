package com.mindconnect.application.professional.professional.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.professional.professional.command.RegisterProfessionalCommand;
import com.mindconnect.application.professional.professional.dto.ProfessionalResponse;
import com.mindconnect.application.professional.professional.exception.ProfessionalAlreadyExistsApplicationException;
import com.mindconnect.domain.professional.professional.exception.InvalidProfessionalException;
import com.mindconnect.domain.professional.professional.model.aggregate.Professional;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;
import com.mindconnect.domain.professional.professionaltype.model.valueobject.ProfessionalTypeId;

class RegisterProfessionalUseCaseTest {

    @Test
    void shouldRegisterAndPersistProfessional() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        ProfessionalTypeId professionalTypeId = ProfessionalTypeId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        FakeProfessionalRepository repository = new FakeProfessionalRepository();
        RegisterProfessionalUseCase useCase = new RegisterProfessionalUseCase(repository);

        ProfessionalResponse response = useCase.execute(
                new RegisterProfessionalCommand(documentTypeId, "12345678", "Juan", "Perez",
                        professionalTypeId, "LIC123456", cityId));

        assertNotNull(response.id());
        assertEquals(documentTypeId.value(), response.documentTypeId());
        assertEquals("12345678", response.documentNumber());
        assertEquals("Juan", response.firstName());
        assertEquals("Perez", response.lastName());
        assertEquals(professionalTypeId.value(), response.professionalTypeId());
        assertEquals("LIC123456", response.licenseNumber());
        assertTrue(response.active());
        assertEquals(cityId.value(), response.cityId());
        assertEquals(1, repository.size());
    }

    @Test
    void shouldRejectDuplicatedLicenseNumber() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        ProfessionalTypeId professionalTypeId = ProfessionalTypeId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        FakeProfessionalRepository repository =
                new FakeProfessionalRepository().with(
                        Professional.register(
                                documentTypeId, "12345678", "Juan", "Perez",
                                professionalTypeId, "LIC123", cityId));
        RegisterProfessionalUseCase useCase = new RegisterProfessionalUseCase(repository);

        assertThrows(ProfessionalAlreadyExistsApplicationException.class,
                () -> useCase.execute(
                        new RegisterProfessionalCommand(
                                documentTypeId, "87654321", "Maria", "Gomez",
                                professionalTypeId, "  LIC123  ", cityId)));
        assertEquals(1, repository.size());
    }

    @Test
    void shouldRejectDuplicatedCompositeDocument() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        ProfessionalTypeId professionalTypeId = ProfessionalTypeId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        FakeProfessionalRepository repository =
                new FakeProfessionalRepository().with(
                        Professional.register(
                                documentTypeId, "12345678", "Juan", "Perez",
                                professionalTypeId, "LIC123", cityId));
        RegisterProfessionalUseCase useCase = new RegisterProfessionalUseCase(repository);

        assertThrows(ProfessionalAlreadyExistsApplicationException.class,
                () -> useCase.execute(
                        new RegisterProfessionalCommand(
                                documentTypeId, " 12345678 ", "Maria", "Gomez",
                                professionalTypeId, "LIC456", cityId)));
        assertEquals(1, repository.size());
    }

    @Test
    void shouldPropagateDomainValidation() {
        RegisterProfessionalUseCase useCase = new RegisterProfessionalUseCase(new FakeProfessionalRepository());

        assertThrows(InvalidProfessionalException.class,
                () -> useCase.execute(
                        new RegisterProfessionalCommand(
                                DocumentTypeId.generate(), "  ", "Juan", "Perez",
                                ProfessionalTypeId.generate(), "LIC123", CityMunicipalityId.generate())));
    }
}