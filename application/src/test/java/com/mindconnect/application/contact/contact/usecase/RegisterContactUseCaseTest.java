package com.mindconnect.application.contact.contact.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.contact.contact.command.RegisterContactCommand;
import com.mindconnect.application.contact.contact.dto.ContactResponse;
import com.mindconnect.domain.contact.contact.exception.InvalidContactException;
import com.mindconnect.domain.contact.contact.model.aggregate.Contact;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;

class RegisterContactUseCaseTest {

    @Test
    void shouldRegisterAndPersistContact() {
        CityMunicipalityId cityId = CityMunicipalityId.generate();
        FakeContactRepository repository = new FakeContactRepository();
        RegisterContactUseCase useCase = new RegisterContactUseCase(repository);

        ContactResponse response = useCase.execute(
                new RegisterContactCommand("Juan Perez", "juan@example.com", "Notes", cityId, ProfessionalId.generate()));

        assertNotNull(response.id());
        assertEquals("Juan Perez", response.fullName());
        assertEquals("juan@example.com", response.email());
        assertEquals("Notes", response.notes());
        assertEquals(1, repository.size());
    }

    @Test
    void shouldPropagateDomainValidation() {
        RegisterContactUseCase useCase = new RegisterContactUseCase(new FakeContactRepository());

        assertThrows(InvalidContactException.class,
                () -> useCase.execute(new RegisterContactCommand("  ", "juan@example.com", null, CityMunicipalityId.generate(), ProfessionalId.generate())));
    }
}