package com.mindconnect.infrastructure.professional.professional.adapters.out.persistence.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindconnect.infrastructure.professional.professional.adapters.out.persistence.entity.ProfessionalJpaEntity;

public interface ProfessionalJpaRepository extends JpaRepository<ProfessionalJpaEntity, UUID> {

    boolean existsByLicenseNumber(String licenseNumber);

    boolean existsByLicenseNumberAndIdNot(String licenseNumber, UUID id);

    boolean existsByDocumentTypeIdAndDocumentNumber(UUID documentTypeId, String documentNumber);

    boolean existsByDocumentTypeIdAndDocumentNumberAndIdNot(UUID documentTypeId, String documentNumber, UUID id);
}