package com.mindconnect.infrastructure.clinicalrecord.clinicalrecord.adapters.out.persistence.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindconnect.infrastructure.clinicalrecord.clinicalrecord.adapters.out.persistence.entity.ClinicalRecordJpaEntity;

public interface ClinicalRecordJpaRepository extends JpaRepository<ClinicalRecordJpaEntity, UUID> {

    List<ClinicalRecordJpaEntity> findByPatientId(UUID patientId);
}