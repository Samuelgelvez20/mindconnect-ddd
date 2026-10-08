package com.mindconnect.infrastructure.clinicalrecord.mentalstatusexam.adapters.in.rest.dtos;

import jakarta.validation.constraints.Size;
import java.util.UUID;

public record UpdateMentalStatusExamRequest(

        @Size(max = 36, message = "encounterId must not exceed 36 characters")
        UUID encounterId,

        @Size(max = 100, message = "appearance must not exceed 100 characters")
        String appearance,

        @Size(max = 100, message = "behavior must not exceed 100 characters")
        String behavior,

        @Size(max = 100, message = "attitude must not exceed 100 characters")
        String attitude,

        @Size(max = 100, message = "consciousness must not exceed 100 characters")
        String consciousness,

        @Size(max = 100, message = "orientation must not exceed 100 characters")
        String orientation,

        @Size(max = 100, message = "attention must not exceed 100 characters")
        String attention,

        @Size(max = 100, message = "memory must not exceed 100 characters")
        String memory,

        @Size(max = 100, message = "speech must not exceed 100 characters")
        String speech,

        @Size(max = 100, message = "mood must not exceed 100 characters")
        String mood,

        @Size(max = 100, message = "affect must not exceed 100 characters")
        String affect,

        @Size(max = 100, message = "thoughtProcess must not exceed 100 characters")
        String thoughtProcess,

        @Size(max = 100, message = "thoughtContent must not exceed 100 characters")
        String thoughtContent,

        @Size(max = 100, message = "perception must not exceed 100 characters")
        String perception,

        @Size(max = 100, message = "judgment must not exceed 100 characters")
        String judgment,

        @Size(max = 100, message = "insight must not exceed 100 characters")
        String insight,

        @Size(max = 100, message = "psychomotorActivity must not exceed 100 characters")
        String psychomotorActivity,

        @Size(max = 100, message = "observations must not exceed 100 characters")
        String observations,

        @Size(max = 36, message = "createdBy must not exceed 36 characters")
        UUID createdBy
) {
}