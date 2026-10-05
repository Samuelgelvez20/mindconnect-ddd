package com.mindconnect.application.contact.relationshiptype.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.contact.relationshiptype.command.UpdateRelationshipTypeCommand;
import com.mindconnect.application.contact.relationshiptype.dto.RelationshipTypeResponse;
import com.mindconnect.application.contact.relationshiptype.exception.RelationshipTypeAlreadyExistsApplicationException;
import com.mindconnect.application.contact.relationshiptype.exception.RelationshipTypeNotFoundApplicationException;
import com.mindconnect.domain.contact.relationshiptype.model.aggregate.RelationshipType;
import com.mindconnect.domain.contact.relationshiptype.model.valueobject.RelationshipTypeId;

class UpdateRelationshipTypeUseCaseTest {

    @Test
    void shouldUpdateExistingRelationshipType() {
        RelationshipType relationshipType = RelationshipType.register("Father");
        FakeRelationshipTypeRepository repository = new FakeRelationshipTypeRepository().with(relationshipType);
        UpdateRelationshipTypeUseCase useCase = new UpdateRelationshipTypeUseCase(repository);

        RelationshipTypeResponse response = useCase.execute(
                new UpdateRelationshipTypeCommand(relationshipType.id(), "Mother"));

        assertEquals("Mother", response.description());
    }

    @Test
    void shouldAllowKeepingTheSameDescription() {
        RelationshipType relationshipType = RelationshipType.register("Father");
        UpdateRelationshipTypeUseCase useCase =
                new UpdateRelationshipTypeUseCase(new FakeRelationshipTypeRepository().with(relationshipType));

        RelationshipTypeResponse response = useCase.execute(
                new UpdateRelationshipTypeCommand(relationshipType.id(), "Father"));

        assertEquals("Father", response.description());
    }

    @Test
    void shouldRejectWhenRelationshipTypeDoesNotExist() {
        UpdateRelationshipTypeUseCase useCase = new UpdateRelationshipTypeUseCase(new FakeRelationshipTypeRepository());

        assertThrows(RelationshipTypeNotFoundApplicationException.class,
                () -> useCase.execute(
                        new UpdateRelationshipTypeCommand(RelationshipTypeId.generate(), "Mother")));
    }

    @Test
    void shouldRejectDescriptionUsedByAnotherRelationshipType() {
        RelationshipType father = RelationshipType.register("Father");
        RelationshipType mother = RelationshipType.register("Mother");
        UpdateRelationshipTypeUseCase useCase =
                new UpdateRelationshipTypeUseCase(new FakeRelationshipTypeRepository().with(father, mother));

        assertThrows(RelationshipTypeAlreadyExistsApplicationException.class,
                () -> useCase.execute(
                        new UpdateRelationshipTypeCommand(mother.id(), "Father")));
    }
}