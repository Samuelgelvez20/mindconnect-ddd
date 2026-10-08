package com.mindconnect.application.contact.relationshiptype.usecase;

import java.util.List;

import com.mindconnect.application.contact.relationshiptype.dto.RelationshipTypeResponse;
import com.mindconnect.domain.contact.relationshiptype.port.repository.RelationshipTypeRepository;

public class ListRelationshipTypeUseCase {

    private final RelationshipTypeRepository relationshipTypeRepository;

    public ListRelationshipTypeUseCase(RelationshipTypeRepository relationshipTypeRepository) {
        this.relationshipTypeRepository = relationshipTypeRepository;
    }

    public List<RelationshipTypeResponse> execute() {
        return relationshipTypeRepository.findAll()
                .stream()
                .map(RelationshipTypeResponse::from)
                .toList();
    }
}