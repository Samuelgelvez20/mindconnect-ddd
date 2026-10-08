package com.mindconnect.application.professional.professionalstudy.command;

import java.util.Objects;

import com.mindconnect.domain.professional.professionalstudy.model.valueobject.ProfessionalStudyId;
import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;

public record UpdateProfessionalStudyCommand(
        ProfessionalStudyId id,
        String title,
        String university,
        boolean isValid,
        String resolutionNumber,
        CountryId countryId) {

    public UpdateProfessionalStudyCommand {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(university, "university must not be null");
        Objects.requireNonNull(countryId, "countryId must not be null");
    }
}