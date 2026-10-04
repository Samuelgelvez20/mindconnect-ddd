package com.mindconnect.infrastructure.referencedata.citymunicipality.adapters.in.rest.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateCityMunicipalityRequest(

        @NotBlank(message = "name is required")
        @Size(max = 50, message = "name must have at most 50 characters")
        String name,

        @NotBlank(message = "code is required")
        @Size(max = 10, message = "code must have at most 10 characters")
        String code,

        @Size(max = 100, message = "description must have at most 100 characters")
        String description,

        @NotNull(message = "active is required")
        Boolean active
) {
}