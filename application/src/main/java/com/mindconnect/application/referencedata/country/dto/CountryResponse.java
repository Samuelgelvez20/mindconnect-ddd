package com.mindconnect.application.referencedata.country.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.referencedata.country.model.aggregate.Country;

public record CountryResponse(
        UUID id,
        String name,
        String code,
        String description,
        String telephonePrefix,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {

    public static CountryResponse from(Country country) {
        return new CountryResponse(
                country.id().value(),
                country.name(),
                country.code(),
                country.description(),
                country.telephonePrefix(),
                country.active(),
                country.createdAt(),
                country.updatedAt()
        );
    }
}
