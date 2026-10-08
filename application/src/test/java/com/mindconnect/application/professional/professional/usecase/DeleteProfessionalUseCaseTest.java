package com.mindconnect.application.professional.professional.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.professional.professional.exception.ProfessionalNotFoundApplicationException;
import com.mindconnect.domain.professional.professional.event.ProfessionalDeletedEvent;
import com.mindconnect.domain.professional.professional.model.aggregate.Professional;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;
import com.mindconnect.domain.professional.professionaltype.model.valueobject.ProfessionalTypeId;

class DeleteProfessionalUseCaseTest {

    @Test
    void shouldDeleteExistingProfessionalAndRecordEvent() {
        DocumentTypeId documentTypeId = DocumentTypeId.generate();
        ProfessionalTypeId professionalTypeId = ProfessionalTypeId.generate();
        CityMunicipalityId cityId = CityMunicipalityId.generate();

        Professional professional = Professional.register(
                documentTypeId, "12345678", "Juan", "Perez", professionalTypeId, "LIC123", cityId);
        FakeProfessionalRepository repository = new FakeProfessionalRepository().with(professional);
        DeleteProfessionalUseCase useCase = new DeleteProfessionalUseCase(repository);

        useCase.execute(professional.id());

        assertEquals(1, repository.deleted().size());
        assertSame(professional, repository.deleted().getFirst());
        assertEquals(0, repository.size());
        assertInstanceOf(ProfessionalDeletedEvent.class, professional.domainEvents().getLast());
    }

    @Test
    void shouldRejectWhenProfessionalDoesNotExist() {
        FakeProfessionalRepository repository = new FakeProfessionalRepository();
        DeleteProfessionalUseCase useCase = new DeleteProfessionalUseCase(repository);

        assertThrows(ProfessionalNotFoundApplicationException.class,
                () -> useCase.execute(ProfessionalId.generate()));
        assertTrue(repository.deleted().isEmpty());
    }
}