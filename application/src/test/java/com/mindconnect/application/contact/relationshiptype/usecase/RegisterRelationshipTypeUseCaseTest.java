package com.mindconnect.application.contact.relationshiptype.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.contact.relationshiptype.command.RegisterRelationshipTypeCommand;
import com.mindconnect.application.contact.relationshiptype.dto.RelationshipTypeResponse;
import com.mindconnect.application.contact.relationshiptype.exception.RelationshipTypeAlreadyExistsApplicationException;
import com.mindconnect.domain.contact.relationshiptype.exception.InvalidRelationshipTypeException;
import com.mindconnect.domain.contact.relationshiptype.model.aggregate.RelationshipType;

class RegisterRelationshipTypeUseCaseTest {

    @Test
    void shouldRegisterAndPersistRelationshipType() {
        FakeRelationshipTypeRepository repository = new FakeRelationshipTypeRepository();
        RegisterRelationshipTypeUseCase useCase = new RegisterRelationshipTypeUseCase(repository);

        RelationshipTypeResponse response = useCase.execute(new RegisterRelationshipTypeCommand("Father"));

        assertNotNull(response.id());
        assertEquals("Father", response.description());
        assertEquals(1, repository.size());
    }

    @Test
    void shouldRejectDuplicatedDescription() {
        FakeRelationshipTypeRepository repository =
                new FakeRelationshipTypeRepository().with(RelationshipType.register("Father"));
        RegisterRelationshipTypeUseCase useCase = new RegisterRelationshipTypeUseCase(repository);

        assertThrows(RelationshipTypeAlreadyExistsApplicationException.class,
                () -> useCase.execute(new RegisterRelationshipTypeCommand("  Father  ")));
        assertEquals(1, repository.size());
    }

    @Test
    void shouldPropagateDomainValidation() {
        RegisterRelationshipTypeUseCase useCase = new RegisterRelationshipTypeUseCase(new FakeRelationshipTypeRepository());

        assertThrows(InvalidRelationshipTypeException.class,
                () -> useCase.execute(new RegisterRelationshipTypeCommand("  ")));
    }
}