package com.mindconnect.infrastructure.professional.professionaltype.adapters.out.persistence.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindconnect.infrastructure.professional.professionaltype.adapters.out.persistence.entity.ProfessionalTypeJpaEntity;

public interface ProfessionalTypeJpaRepository extends JpaRepository<ProfessionalTypeJpaEntity, UUID> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, UUID id);
}