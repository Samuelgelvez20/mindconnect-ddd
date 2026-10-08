package com.mindconnect.infrastructure.clinicalrecord.mentalstatusexam.adapters.out.persistence.mappers;

import com.mindconnect.domain.clinicalrecord.mentalstatusexam.model.aggregate.MentalStatusExam;
import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;
import com.mindconnect.domain.clinicalrecord.mentalstatusexam.model.valueobject.MentalStatusExamId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.infrastructure.clinicalrecord.mentalstatusexam.adapters.out.persistence.entity.MentalStatusExamJpaEntity;

public class MentalStatusExamPersistenceMapper {

    public MentalStatusExam toDomain(MentalStatusExamJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        return MentalStatusExam.restore(
                new MentalStatusExamId(entity.getId()),
                new EncounterId(entity.getEncounterId()),
                entity.getAppearance(),
                entity.getBehavior(),
                entity.getAttitude(),
                entity.getConsciousness(),
                entity.getOrientation(),
                entity.getAttention(),
                entity.getMemory(),
                entity.getSpeech(),
                entity.getMood(),
                entity.getAffect(),
                entity.getThoughtProcess(),
                entity.getThoughtContent(),
                entity.getPerception(),
                entity.getJudgment(),
                entity.getInsight(),
                entity.getPsychomotorActivity(),
                entity.getObservations(),
                new ProfessionalId(entity.getCreatedBy()),
                entity.getCreatedAt(),
                entity.getCreatedAt()
        );
    }

    public MentalStatusExamJpaEntity toJpa(MentalStatusExam exam) {
        if (exam == null) {
            return null;
        }
        MentalStatusExamJpaEntity entity = new MentalStatusExamJpaEntity();
        entity.setId(exam.id().value());
        entity.setEncounterId(exam.encounterId().value());
        entity.setAppearance(exam.appearance());
        entity.setBehavior(exam.behavior());
        entity.setAttitude(exam.attitude());
        entity.setConsciousness(exam.consciousness());
        entity.setOrientation(exam.orientation());
        entity.setAttention(exam.attention());
        entity.setMemory(exam.memory());
        entity.setSpeech(exam.speech());
        entity.setMood(exam.mood());
        entity.setAffect(exam.affect());
        entity.setThoughtProcess(exam.thoughtProcess());
        entity.setThoughtContent(exam.thoughtContent());
        entity.setPerception(exam.perception());
        entity.setJudgment(exam.judgment());
        entity.setInsight(exam.insight());
        entity.setPsychomotorActivity(exam.psychomotorActivity());
        entity.setObservations(exam.observations());
        entity.setCreatedBy(exam.createdBy().value());
        entity.setCreatedAt(exam.createdAt());
        return entity;
    }
}