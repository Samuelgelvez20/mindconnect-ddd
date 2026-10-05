package com.mindconnect.infrastructure.contact.contact.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateContactRequest(

        @NotBlank(message = "fullName is required")
        @Size(max = 200, message = "fullName must have at most 200 characters")
        String fullName,

        @Size(max = 150, message = "email must have at most 150 characters")
        String email,

        String notes,

        @NotNull(message = "cityId is required")
        java.util.UUID cityId,

        @NotNull(message = "updatedBy is required")
        java.util.UUID updatedBy
) {
}