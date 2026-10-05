package com.mindconnect.application.clinicalrecord.mentalstatusexam.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.clinicalrecord.mentalstatusexam.model.aggregate.MentalStatusExam;

public record MentalStatusExamResponse(
        UUID id,
        UUID encounterId,
        String appearance,
        String behavior,
        String attitude,
        String consciousness,
        String orientation,
        String attention,
        String memory,
        String speech,
        String mood,
        String affect,
        String thoughtProcess,
        String thoughtContent,
        String perception,
        String judgment,
        String insight,
        String psychomotorActivity,
        String observations,
        UUID createdBy,
        Instant createdAt,
        Instant updatedAt
) {

    public static MentalStatusExamResponse from(MentalStatusExam exam) {
        return new MentalStatusExamResponse(
                exam.id().value(),
                exam.encounterId().value(),
                exam.appearance(),
                exam.behavior(),
                exam.attitude(),
                exam.consciousness(),
                exam.orientation(),
                exam.attention(),
                exam.memory(),
                exam.speech(),
                exam.mood(),
                exam.affect(),
                exam.thoughtProcess(),
                exam.thoughtContent(),
                exam.perception(),
                exam.judgment(),
                exam.insight(),
                exam.psychomotorActivity(),
                exam.observations(),
                exam.createdBy().value(),
                exam.createdAt(),
                exam.updatedAt()
        );
    }
}