package com.mindconnect.infrastructure.patient.patientallergy.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdatePatientAllergyRequest(

        @NotBlank(message = "substance is required")
        @Size(max = 200, message = "substance must have at most 200 characters")
        String substance,

        @Size(max = 65535, message = "reaction must have at most 65535 characters")
        String reaction,

        @Size(max = 20, message = "severity must have at most 20 characters")
        String severity,

        @NotNull(message = "active is required")
        Boolean active
) {
}