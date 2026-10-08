package com.mindconnect.infrastructure.contact.phonecontact.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatePhoneContactRequest(

        @NotNull(message = "contactId is required")
        java.util.UUID contactId,

        @NotBlank(message = "phone is required")
        @Size(max = 30, message = "phone must have at most 30 characters")
        String phone,

        String notes
) {
}