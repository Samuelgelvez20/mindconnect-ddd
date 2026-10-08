package com.mindconnect.infrastructure.clinicalrecord.clinicalnote.adapters.out.persistence.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindconnect.infrastructure.clinicalrecord.clinicalnote.adapters.out.persistence.entity.ClinicalNoteJpaEntity;

public interface ClinicalNoteJpaRepository extends JpaRepository<ClinicalNoteJpaEntity, UUID> {

    List<ClinicalNoteJpaEntity> findByEncounterId(UUID encounterId);
}