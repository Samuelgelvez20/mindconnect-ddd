package com.mindconnect.infrastructure.clinicalrecord.mentalstatusexam.adapters.out.persistence.repositories;

import com.mindconnect.domain.clinicalrecord.mentalstatusexam.model.aggregate.MentalStatusExam;
import com.mindconnect.domain.clinicalrecord.mentalstatusexam.model.valueobject.MentalStatusExamId;
import com.mindconnect.domain.clinicalrecord.mentalstatusexam.port.repository.MentalStatusExamRepository;
import com.mindconnect.infrastructure.clinicalrecord.mentalstatusexam.adapters.out.persistence.entity.MentalStatusExamJpaEntity;
import com.mindconnect.infrastructure.clinicalrecord.mentalstatusexam.adapters.out.persistence.mappers.MentalStatusExamPersistenceMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MentalStatusExamJpaRepository extends JpaRepository<MentalStatusExamJpaEntity, UUID> {

    Optional<MentalStatusExamJpaEntity> findByEncounterId(UUID encounterId);
}