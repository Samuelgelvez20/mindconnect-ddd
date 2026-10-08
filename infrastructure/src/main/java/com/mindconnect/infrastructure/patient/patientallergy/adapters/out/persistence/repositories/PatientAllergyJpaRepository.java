package com.mindconnect.infrastructure.patient.patientallergy.adapters.out.persistence.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindconnect.infrastructure.patient.patientallergy.adapters.out.persistence.entity.PatientAllergyJpaEntity;

public interface PatientAllergyJpaRepository extends JpaRepository<PatientAllergyJpaEntity, UUID> {
}