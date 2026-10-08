package com.mindconnect.infrastructure.clinicalrecord.risklevel.adapters.in.rest.dtos;

import jakarta.validation.constraints.*;
import java.util.UUID;

public record CreateRiskLevelRequest(

        @NotBlank(message = "code must not be blank")
        @Size(max = 20, message = "code must not exceed 20 characters")
        String code,

        @NotBlank(message = "name must not be blank")
        @Size(max = 50, message = "name must not exceed 50 characters")
        String name,

        @Max(value = 10, message = "severity must not exceed 10")
        Integer severity
) {
}