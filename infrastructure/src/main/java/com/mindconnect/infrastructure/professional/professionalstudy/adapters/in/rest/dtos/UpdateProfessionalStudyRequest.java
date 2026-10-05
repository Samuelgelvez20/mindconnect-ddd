package com.mindconnect.infrastructure.professional.professionalstudy.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateProfessionalStudyRequest(

        @NotBlank(message = "title is required")
        @Size(max = 100, message = "title must have at most 100 characters")
        String title,

        @NotBlank(message = "university is required")
        @Size(max = 100, message = "university must have at most 100 characters")
        String university,

        @NotNull(message = "isValid is required")
        Boolean isValid,

        @Size(max = 60, message = "resolutionNumber must have at most 60 characters")
        String resolutionNumber,

        @NotNull(message = "countryId is required")
        java.util.UUID countryId
) {
}