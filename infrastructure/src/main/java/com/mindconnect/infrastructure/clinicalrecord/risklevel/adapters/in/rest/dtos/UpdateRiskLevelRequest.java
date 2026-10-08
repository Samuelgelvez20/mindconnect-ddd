package com.mindconnect.infrastructure.clinicalrecord.risklevel.adapters.in.rest.dtos;

import jakarta.validation.constraints.*;
import java.util.UUID;

public record UpdateRiskLevelRequest(

        @Size(max = 20, message = "code must not exceed 20 characters")
        String code,

        @Size(max = 50, message = "name must not exceed 50 characters")
        String name,

        @Max(value = 10, message = "severity must not exceed 10")
        Integer severity,

        @NotNull(message = "active is required")
        Boolean active
) {
}