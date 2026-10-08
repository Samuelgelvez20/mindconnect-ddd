package com.mindconnect.infrastructure.referencedata.citymunicipality.adapters.out.persistence.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindconnect.infrastructure.referencedata.citymunicipality.adapters.out.persistence.entity.CityMunicipalityJpaEntity;

public interface CityMunicipalityJpaRepository extends JpaRepository<CityMunicipalityJpaEntity, UUID> {

    boolean existsByRegionIdAndCode(UUID regionId, String code);

    boolean existsByRegionIdAndCodeAndIdNot(UUID regionId, String code, UUID id);
}