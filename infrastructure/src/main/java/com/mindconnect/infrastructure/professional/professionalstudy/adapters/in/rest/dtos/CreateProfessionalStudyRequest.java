package com.mindconnect.infrastructure.professional.professionalstudy.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateProfessionalStudyRequest(

        @NotNull(message = "studyId is required")
        java.util.UUID studyId,

        @NotNull(message = "professionalId is required")
        java.util.UUID professionalId,

        @NotBlank(message = "title is required")
        @Size(max = 100, message = "title must have at most 100 characters")
        String title,

        @NotBlank(message = "university is required")
        @Size(max = 100, message = "university must have at most 100 characters")
        String university,

        @NotNull(message = "countryId is required")
        java.util.UUID countryId
) {
}