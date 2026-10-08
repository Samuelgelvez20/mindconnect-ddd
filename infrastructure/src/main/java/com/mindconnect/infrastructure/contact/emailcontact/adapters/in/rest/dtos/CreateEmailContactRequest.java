package com.mindconnect.infrastructure.contact.emailcontact.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateEmailContactRequest(

        @NotNull(message = "contactId is required")
        java.util.UUID contactId,

        @NotBlank(message = "email is required")
        @Size(max = 150, message = "email must have at most 150 characters")
        String email,

        String notes
) {
}