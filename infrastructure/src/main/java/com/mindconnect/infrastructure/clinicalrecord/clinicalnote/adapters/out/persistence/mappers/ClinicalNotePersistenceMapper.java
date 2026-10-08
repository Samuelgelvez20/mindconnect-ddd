package com.mindconnect.infrastructure.clinicalrecord.clinicalnote.adapters.out.persistence.mappers;

import com.mindconnect.domain.clinicalrecord.clinicalnote.model.aggregate.ClinicalNote;
import com.mindconnect.domain.clinicalrecord.clinicalnote.model.valueobject.ClinicalNoteId;
import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.infrastructure.clinicalrecord.clinicalnote.adapters.out.persistence.entity.ClinicalNoteJpaEntity;

public class ClinicalNotePersistenceMapper {

    public ClinicalNote toDomain(ClinicalNoteJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        return ClinicalNote.restore(
                new ClinicalNoteId(entity.getId()),
                new EncounterId(entity.getEncounterId()),
                new ProfessionalId(entity.getProfessionalId()),
                entity.getSubjective(),
                entity.getObjective(),
                entity.getAssessment(),
                entity.getPlan(),
                entity.getAdditionalNotes(),
                entity.getSignedAt(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public ClinicalNoteJpaEntity toJpa(ClinicalNote note) {
        if (note == null) {
            return null;
        }
        ClinicalNoteJpaEntity entity = new ClinicalNoteJpaEntity();
        entity.setId(note.id().value());
        entity.setEncounterId(note.encounterId().value());
        entity.setProfessionalId(note.professionalId().value());
        entity.setSubjective(note.subjective());
        entity.setObjective(note.objective());
        entity.setAssessment(note.assessment());
        entity.setPlan(note.plan());
        entity.setAdditionalNotes(note.additionalNotes());
        entity.setSignedAt(note.signedAt());
        entity.setCreatedAt(note.createdAt());
        entity.setUpdatedAt(note.updatedAt());
        return entity;
    }
}