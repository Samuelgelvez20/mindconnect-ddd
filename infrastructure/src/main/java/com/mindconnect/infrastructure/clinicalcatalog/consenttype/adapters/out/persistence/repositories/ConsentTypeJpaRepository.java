package com.mindconnect.infrastructure.clinicalcatalog.consenttype.adapters.out.persistence.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindconnect.infrastructure.clinicalcatalog.consenttype.adapters.out.persistence.entity.ConsentTypeJpaEntity;

public interface ConsentTypeJpaRepository extends JpaRepository<ConsentTypeJpaEntity, UUID> {

    Optional<ConsentTypeJpaEntity> findByCode(String code);

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, UUID id);
}