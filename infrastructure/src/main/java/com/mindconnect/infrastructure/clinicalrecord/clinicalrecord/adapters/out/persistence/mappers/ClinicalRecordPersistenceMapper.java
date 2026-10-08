package com.mindconnect.infrastructure.clinicalrecord.clinicalrecord.adapters.out.persistence.mappers;

import java.time.Instant;

import com.mindconnect.domain.clinicalrecord.clinicalrecord.model.aggregate.ClinicalRecord;
import com.mindconnect.domain.clinicalrecord.clinicalrecord.model.valueobject.ClinicalRecordId;
import com.mindconnect.domain.clinicalrecord.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;
import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.infrastructure.clinicalrecord.clinicalrecord.adapters.out.persistence.entity.ClinicalRecordJpaEntity;

public class ClinicalRecordPersistenceMapper {

    public ClinicalRecordJpaEntity toJpa(ClinicalRecord domain) {
        if (domain == null) {
            return null;
        }

        ClinicalRecordJpaEntity jpa = new ClinicalRecordJpaEntity();
        jpa.setId(domain.id().value());
        jpa.setPatientId(domain.patientId().value());
        jpa.setCreationDate(domain.creationDate());
        jpa.setRecordNumber(domain.recordNumber());
        jpa.setOpenedAt(domain.openedAt());
        jpa.setClosedAt(domain.closedAt());
        jpa.setStatusId(domain.statusId().value());
        jpa.setCreatedAt(domain.createdAt());
        jpa.setCreatedBy(domain.createdBy().value());
        return jpa;
    }

    public ClinicalRecord toDomain(ClinicalRecordJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        Instant updatedAt = jpa.getCreatedAt(); // fallback: V18 has no updated_at column
        return ClinicalRecord.restore(
                new ClinicalRecordId(jpa.getId()),
                new PatientId(jpa.getPatientId()),
                jpa.getCreationDate(),
                jpa.getRecordNumber(),
                jpa.getOpenedAt(),
                jpa.getClosedAt(),
                new ClinicalRecordStatusId(jpa.getStatusId()),
                new ProfessionalId(jpa.getCreatedBy()),
                jpa.getCreatedAt(),
                updatedAt
        );
    }
}