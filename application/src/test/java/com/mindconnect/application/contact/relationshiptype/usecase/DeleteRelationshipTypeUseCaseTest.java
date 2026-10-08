package com.mindconnect.application.contact.relationshiptype.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mindconnect.application.contact.relationshiptype.exception.RelationshipTypeNotFoundApplicationException;
import com.mindconnect.domain.contact.relationshiptype.event.RelationshipTypeDeletedEvent;
import com.mindconnect.domain.contact.relationshiptype.model.aggregate.RelationshipType;
import com.mindconnect.domain.contact.relationshiptype.model.valueobject.RelationshipTypeId;

class DeleteRelationshipTypeUseCaseTest {

    @Test
    void shouldDeleteExistingRelationshipTypeAndRecordEvent() {
        RelationshipType relationshipType = RelationshipType.register("Father");
        FakeRelationshipTypeRepository repository = new FakeRelationshipTypeRepository().with(relationshipType);
        DeleteRelationshipTypeUseCase useCase = new DeleteRelationshipTypeUseCase(repository);

        useCase.execute(relationshipType.id());

        assertEquals(1, repository.deleted().size());
        assertSame(relationshipType, repository.deleted().getFirst());
        assertEquals(0, repository.size());
        assertInstanceOf(RelationshipTypeDeletedEvent.class, relationshipType.domainEvents().getLast());
    }

    @Test
    void shouldRejectWhenRelationshipTypeDoesNotExist() {
        FakeRelationshipTypeRepository repository = new FakeRelationshipTypeRepository();
        DeleteRelationshipTypeUseCase useCase = new DeleteRelationshipTypeUseCase(repository);

        assertThrows(RelationshipTypeNotFoundApplicationException.class,
                () -> useCase.execute(RelationshipTypeId.generate()));
        assertTrue(repository.deleted().isEmpty());
    }
}