package com.mindconnect.infrastructure.contact.patientcontact.adapters.out.persistence.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindconnect.infrastructure.contact.patientcontact.adapters.out.persistence.entity.PatientContactJpaEntity;

public interface PatientContactJpaRepository extends JpaRepository<PatientContactJpaEntity, UUID> {
}