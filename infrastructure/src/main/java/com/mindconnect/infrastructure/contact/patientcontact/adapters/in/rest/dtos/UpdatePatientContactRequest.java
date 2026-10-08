package com.mindconnect.infrastructure.contact.patientcontact.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotNull;

public record UpdatePatientContactRequest(

        @NotNull(message = "isPrimaryContact is required")
        Boolean isPrimaryContact,

        @NotNull(message = "isEmergencyContact is required")
        Boolean isEmergencyContact,

        @NotNull(message = "relationshipTypeId is required")
        java.util.UUID relationshipTypeId
) {
}