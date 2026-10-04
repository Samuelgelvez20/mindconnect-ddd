package com.mindconnect.infrastructure.referencedata.gender.adapters.out.persistence.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindconnect.infrastructure.referencedata.gender.adapters.out.persistence.entity.GenderJpaEntity;

public interface GenderJpaRepository extends JpaRepository<GenderJpaEntity, UUID> {

    boolean existsByDescription(String description);

    boolean existsByDescriptionAndIdNot(String description, UUID id);
}