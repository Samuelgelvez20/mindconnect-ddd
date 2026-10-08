package com.mindconnect.application.patient.patientallergy.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.patient.patientallergy.model.aggregate.PatientAllergy;
import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;

public record PatientAllergyResponse(
        UUID id,
        UUID patientId,
        String substance,
        String reaction,
        String severity,
        boolean isValid,
        String resolutionNumber,
        boolean active,
        Instant recordedAt,
        UUID recordedBy,
        Instant createdAt,
        Instant updatedAt
) {

    public static PatientAllergyResponse from(PatientAllergy patientAllergy) {
        return new PatientAllergyResponse(
                patientAllergy.id().value(),
                patientAllergy.patientId().value(),
                patientAllergy.substance(),
                patientAllergy.reaction(),
                patientAllergy.severity(),
                patientAllergy.isValid(),
                patientAllergy.resolutionNumber(),
                patientAllergy.active(),
                patientAllergy.recordedAt(),
                patientAllergy.recordedBy().value(),
                patientAllergy.createdAt(),
                patientAllergy.updatedAt()
        );
    }
}