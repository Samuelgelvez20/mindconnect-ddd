package com.mindconnect.infrastructure.contact.patientcontact.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotNull;

public record CreatePatientContactRequest(

        @NotNull(message = "contactId is required")
        java.util.UUID contactId,

        @NotNull(message = "patientId is required")
        java.util.UUID patientId,

        @NotNull(message = "relationshipTypeId is required")
        java.util.UUID relationshipTypeId
) {
}