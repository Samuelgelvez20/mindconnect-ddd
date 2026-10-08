package com.mindconnect.infrastructure.referencedata.country.adapters.out.persistence.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindconnect.infrastructure.referencedata.country.adapters.out.persistence.entity.CountryJpaEntity;

public interface CountryJpaRepository extends JpaRepository<CountryJpaEntity, UUID> {

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, UUID id);
}
