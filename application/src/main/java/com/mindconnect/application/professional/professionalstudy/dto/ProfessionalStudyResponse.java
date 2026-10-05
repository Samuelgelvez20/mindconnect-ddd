package com.mindconnect.application.professional.professionalstudy.dto;

import java.time.Instant;
import java.util.UUID;

import com.mindconnect.domain.professional.professionalstudy.model.aggregate.ProfessionalStudy;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.professional.study.model.valueobject.StudyId;
import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;

public record ProfessionalStudyResponse(
        UUID id,
        UUID studyId,
        UUID professionalId,
        String title,
        String university,
        boolean isValid,
        String resolutionNumber,
        UUID countryId,
        Instant createdAt,
        Instant updatedAt
) {

    public static ProfessionalStudyResponse from(ProfessionalStudy professionalStudy) {
        return new ProfessionalStudyResponse(
                professionalStudy.id().value(),
                professionalStudy.studyId().value(),
                professionalStudy.professionalId().value(),
                professionalStudy.title(),
                professionalStudy.university(),
                professionalStudy.isValid(),
                professionalStudy.resolutionNumber(),
                professionalStudy.countryId().value(),
                professionalStudy.createdAt(),
                professionalStudy.updatedAt()
        );
    }
}