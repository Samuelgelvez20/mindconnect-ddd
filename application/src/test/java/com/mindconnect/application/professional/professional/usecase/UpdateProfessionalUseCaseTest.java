package com.mindconnect.application.professional.professional.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.professional.professional.command.UpdateProfessionalCommand;
import com.mindconnect.application.professional.professional.dto.ProfessionalResponse;
import com.mindconnect.application.professional.professional.exception.ProfessionalAlreadyExistsApplicationException;
import com.mindconnect.application.professional.professional.exception.ProfessionalNotFoundApplicationException;
import com.mindconnect.domain.professional.professional.model.aggregate.Professional;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;
import com.mindconnect.domain.professional.professionaltype.model.valueobject.ProfessionalTypeId;

class UpdateProfessionalUseCaseTest {

    @Test
    void shouldUpdateExistingProfessional() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        ProfessionalTypeId professionalTypeId = ProfessionalTypeId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        Professional professional = Professional.register(
                documentTypeId, "12345678", "Juan", "Perez", professionalTypeId, "LIC123", cityId);
        FakeProfessionalRepository repository = new FakeProfessionalRepository().with(professional);
        UpdateProfessionalUseCase useCase = new UpdateProfessionalUseCase(repository);

        ProfessionalResponse response = useCase.execute(
                new UpdateProfessionalCommand(
                        professional.id(),
                        documentTypeId,
                        "87654321",
                        "Maria",
                        "Gomez",
                        professionalTypeId,
                        "LIC987",
                        false,
                        cityId));

        assertEquals("Maria", response.firstName());
        assertEquals("Gomez", response.lastName());
        assertEquals("87654321", response.documentNumber());
        assertFalse(response.active());
    }

    @Test
    void shouldAllowKeepingTheSameLicenseNumber() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        ProfessionalTypeId professionalTypeId = ProfessionalTypeId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        Professional professional = Professional.register(
                documentTypeId, "12345678", "Juan", "Perez", professionalTypeId, "LIC123", cityId);
        UpdateProfessionalUseCase useCase =
                new UpdateProfessionalUseCase(new FakeProfessionalRepository().with(professional));

        ProfessionalResponse response = useCase.execute(
                new UpdateProfessionalCommand(
                        professional.id(),
                        documentTypeId,
                        "12345678",
                        "Juan Updated",
                        "Perez",
                        professionalTypeId,
                        "LIC123",
                        true,
                        cityId));

        assertEquals("Juan Updated", response.firstName());
    }

    @Test
    void shouldAllowKeepingTheSameCompositeDocument() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        ProfessionalTypeId professionalTypeId = ProfessionalTypeId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        Professional professional = Professional.register(
                documentTypeId, "12345678", "Juan", "Perez", professionalTypeId, "LIC123", cityId);
        UpdateProfessionalUseCase useCase =
                new UpdateProfessionalUseCase(new FakeProfessionalRepository().with(professional));

        ProfessionalResponse response = useCase.execute(
                new UpdateProfessionalCommand(
                        professional.id(),
                        documentTypeId,
                        "12345678",
                        "Juan Updated",
                        "Perez",
                        professionalTypeId,
                        "LIC123",
                        true,
                        cityId));

        assertEquals("Juan Updated", response.firstName());
    }

    @Test
    void shouldRejectWhenProfessionalDoesNotExist() {
        UpdateProfessionalUseCase useCase = new UpdateProfessionalUseCase(new FakeProfessionalRepository());

        assertThrows(ProfessionalNotFoundApplicationException.class,
                () -> useCase.execute(
                        new UpdateProfessionalCommand(
                                ProfessionalId.generate(),
                                DocumentTypeId.generate(),
                                "12345678",
                                "Maria",
                                "Gomez",
                                ProfessionalTypeId.generate(),
                                "LIC123",
                                true,
                                CityMunicipalityId.generate())));
    }

    @Test
    void shouldRejectLicenseNumberUsedByAnotherProfessional() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        ProfessionalTypeId professionalTypeId = ProfessionalTypeId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        Professional professional1 = Professional.register(
                documentTypeId, "12345678", "Juan", "Perez", professionalTypeId, "LIC123", cityId);
        Professional professional2 = Professional.register(
                documentTypeId, "87654321", "Maria", "Gomez", professionalTypeId, "LIC456", cityId);
        UpdateProfessionalUseCase useCase =
                new UpdateProfessionalUseCase(new FakeProfessionalRepository().with(professional1, professional2));

        assertThrows(ProfessionalAlreadyExistsApplicationException.class,
                () -> useCase.execute(
                        new UpdateProfessionalCommand(
                                professional2.id(),
                                documentTypeId,
                                "87654321",
                                "Maria",
                                "Gomez",
                                professionalTypeId,
                                "LIC123",
                                true,
                                cityId)));
    }

    @Test
    void shouldRejectCompositeDocumentUsedByAnotherProfessional() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        ProfessionalTypeId professionalTypeId = ProfessionalTypeId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        Professional professional1 = Professional.register(
                documentTypeId, "12345678", "Juan", "Perez", professionalTypeId, "LIC123", cityId);
        Professional professional2 = Professional.register(
                documentTypeId, "87654321", "Maria", "Gomez", professionalTypeId, "LIC456", cityId);
        UpdateProfessionalUseCase useCase =
                new UpdateProfessionalUseCase(new FakeProfessionalRepository().with(professional1, professional2));

        assertThrows(ProfessionalAlreadyExistsApplicationException.class,
                () -> useCase.execute(
                        new UpdateProfessionalCommand(
                                professional2.id(),
                                documentTypeId,
                                "12345678",
                                "Maria",
                                "Gomez",
                                professionalTypeId,
                                "LIC456",
                                true,
                                cityId)));
    }
}