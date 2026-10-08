package com.mindconnect.infrastructure.clinicalrecord.encounter.adapters.out.persistence.mappers;

import com.mindconnect.domain.clinicalrecord.encounter.model.aggregate.Encounter;
import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;
import com.mindconnect.domain.clinicalrecord.clinicalrecord.model.valueobject.ClinicalRecordId;
import com.mindconnect.domain.clinicalrecord.encountertype.model.valueobject.EncounterTypeId;
import com.mindconnect.domain.clinicalrecord.encountermodality.model.valueobject.EncounterModalityId;
import com.mindconnect.domain.clinicalrecord.encounterstatus.model.valueobject.EncounterStatusId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.infrastructure.clinicalrecord.encounter.adapters.out.persistence.entity.EncounterJpaEntity;

public class EncounterPersistenceMapper {

    public EncounterJpaEntity toJpa(Encounter domain) {
        if (domain == null) {
            return null;
        }

        EncounterJpaEntity jpa = new EncounterJpaEntity();
        jpa.setId(domain.id().value());
        jpa.setClinicalRecordId(domain.clinicalRecordId().value());
        jpa.setProfessionalId(domain.professionalId().value());
        jpa.setEncounterTypeId(domain.encounterTypeId().value());
        jpa.setStartedAt(domain.startedAt());
        jpa.setEndedAt(domain.endedAt());
        jpa.setReasonForVisit(domain.reasonForVisit());
        jpa.setCurrentCondition(domain.currentCondition());
        jpa.setModalityId(domain.modalityId().value());
        jpa.setStatusId(domain.statusId().value());
        jpa.setCreatedAt(domain.createdAt());
        jpa.setCreatedBy(domain.createdBy().value());
        jpa.setUpdatedAt(domain.updatedAt());
        jpa.setUpdatedBy(domain.updatedBy() != null ? domain.updatedBy().value() : null);
        return jpa;
    }

    public Encounter toDomain(EncounterJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        return Encounter.restore(
                new EncounterId(jpa.getId()),
                new ClinicalRecordId(jpa.getClinicalRecordId()),
                new ProfessionalId(jpa.getProfessionalId()),
                new EncounterTypeId(jpa.getEncounterTypeId()),
                jpa.getStartedAt(),
                jpa.getEndedAt(),
                jpa.getReasonForVisit(),
                jpa.getCurrentCondition(),
                new EncounterModalityId(jpa.getModalityId()),
                new EncounterStatusId(jpa.getStatusId()),
                new ProfessionalId(jpa.getCreatedBy()),
                jpa.getUpdatedBy() != null ? new ProfessionalId(jpa.getUpdatedBy()) : null,
                jpa.getCreatedAt(),
                jpa.getUpdatedAt()
        );
    }
}