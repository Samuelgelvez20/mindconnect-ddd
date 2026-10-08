package com.mindconnect.infrastructure.contact.relationshiptype.adapters.out.persistence.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindconnect.infrastructure.contact.relationshiptype.adapters.out.persistence.entity.RelationshipTypeJpaEntity;

public interface RelationshipTypeJpaRepository extends JpaRepository<RelationshipTypeJpaEntity, UUID> {

    boolean existsByDescription(String description);

    boolean existsByDescriptionAndIdNot(String description, UUID id);
}