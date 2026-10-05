package com.mindconnect.infrastructure.professional.professional.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateProfessionalRequest(

        @NotNull(message = "documentTypeId is required")
        java.util.UUID documentTypeId,

        @NotBlank(message = "documentNumber is required")
        @Size(max = 30, message = "documentNumber must have at most 30 characters")
        String documentNumber,

        @NotBlank(message = "firstName is required")
        @Size(max = 60, message = "firstName must have at most 60 characters")
        String firstName,

        @NotBlank(message = "lastName is required")
        @Size(max = 60, message = "lastName must have at most 60 characters")
        String lastName,

        @NotNull(message = "professionalTypeId is required")
        java.util.UUID professionalTypeId,

        @NotBlank(message = "licenseNumber is required")
        @Size(max = 100, message = "licenseNumber must have at most 100 characters")
        String licenseNumber,

        @NotNull(message = "cityId is required")
        java.util.UUID cityId
) {
}