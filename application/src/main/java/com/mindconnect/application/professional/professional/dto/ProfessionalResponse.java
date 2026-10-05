package com.mindconnect.application.professional.professional.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.professional.professional.model.aggregate.Professional;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;
import com.mindconnect.domain.professional.professionaltype.model.valueobject.ProfessionalTypeId;

public record ProfessionalResponse(
        UUID id,
        UUID documentTypeId,
        String documentNumber,
        String firstName,
        String lastName,
        UUID professionalTypeId,
        String licenseNumber,
        boolean active,
        UUID cityId,
        Instant createdAt,
        Instant updatedAt
) {

    public static ProfessionalResponse from(Professional professional) {
        return new ProfessionalResponse(
                professional.id().value(),
                professional.documentTypeId().value(),
                professional.documentNumber(),
                professional.firstName(),
                professional.lastName(),
                professional.professionalTypeId().value(),
                professional.licenseNumber(),
                professional.active(),
                professional.cityId().value(),
                professional.createdAt(),
                professional.updatedAt()
        );
    }
}