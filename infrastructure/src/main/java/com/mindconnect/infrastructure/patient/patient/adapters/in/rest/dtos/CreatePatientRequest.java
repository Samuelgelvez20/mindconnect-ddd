package com.mindconnect.infrastructure.patient.patient.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatePatientRequest(

        @NotNull(message = "documentTypeId is required")
        java.util.UUID documentTypeId,

        @NotBlank(message = "documentNumber is required")
        @Size(max = 30, message = "documentNumber must have at most 30 characters")
        String documentNumber,

        @NotBlank(message = "firstName is required")
        @Size(max = 50, message = "firstName must have at most 50 characters")
        String firstName,

        @Size(max = 50, message = "middleName must have at most 50 characters")
        String middleName,

        @NotBlank(message = "lastName is required")
        @Size(max = 50, message = "lastName must have at most 50 characters")
        String lastName,

        @Size(max = 50, message = "secondLastName must have at most 50 characters")
        String secondLastName,

        @NotNull(message = "birthDate is required")
        java.time.LocalDate birthDate,

        @NotNull(message = "biologicalSexId is required")
        java.util.UUID biologicalSexId,

        @NotNull(message = "genderIdentityId is required")
        java.util.UUID genderIdentityId,

        @NotBlank(message = "email is required")
        @Size(max = 150, message = "email must have at most 150 characters")
        String email,

        @Size(max = 30, message = "phone must have at most 30 characters")
        String phone,

        @Size(max = 250, message = "address must have at most 250 characters")
        String address,

        @NotNull(message = "createdBy is required")
        java.util.UUID createdBy,

        @NotNull(message = "cityId is required")
        java.util.UUID cityId
) {
}