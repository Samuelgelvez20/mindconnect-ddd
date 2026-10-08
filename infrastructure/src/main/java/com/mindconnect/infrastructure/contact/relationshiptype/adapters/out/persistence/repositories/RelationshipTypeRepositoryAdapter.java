package com.mindconnect.infrastructure.contact.relationshiptype.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.contact.relationshiptype.model.aggregate.RelationshipType;
import com.mindconnect.domain.contact.relationshiptype.model.valueobject.RelationshipTypeId;
import com.mindconnect.domain.contact.relationshiptype.port.repository.RelationshipTypeRepository;
import com.mindconnect.infrastructure.contact.relationshiptype.adapters.out.persistence.mappers.RelationshipTypePersistenceMapper;

public class RelationshipTypeRepositoryAdapter implements RelationshipTypeRepository {

    private final RelationshipTypeJpaRepository relationshipTypeJpaRepository;
    private final RelationshipTypePersistenceMapper mapper;

    public RelationshipTypeRepositoryAdapter(
            RelationshipTypeJpaRepository relationshipTypeJpaRepository,
            RelationshipTypePersistenceMapper mapper) {
        this.relationshipTypeJpaRepository = relationshipTypeJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public RelationshipType save(RelationshipType relationshipType) {
        return mapper.toDomain(relationshipTypeJpaRepository.save(mapper.toJpa(relationshipType)));
    }

    @Override
    public Optional<RelationshipType> findById(RelationshipTypeId id) {
        return relationshipTypeJpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<RelationshipType> findAll() {
        return relationshipTypeJpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existsByDescription(String description) {
        return relationshipTypeJpaRepository.existsByDescription(description);
    }

    @Override
    public boolean existsByDescriptionAndIdNot(String description, RelationshipTypeId id) {
        return relationshipTypeJpaRepository.existsByDescriptionAndIdNot(description, id.value());
    }

    @Override
    public void delete(RelationshipType relationshipType) {
        relationshipTypeJpaRepository.deleteById(relationshipType.id().value());
    }
}