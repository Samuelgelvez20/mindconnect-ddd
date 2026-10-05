package com.mindconnect.application.professional.professionalstudy.command;

import java.util.Objects;

import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.professional.study.model.valueobject.StudyId;
import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;

public record RegisterProfessionalStudyCommand(
        StudyId studyId,
        ProfessionalId professionalId,
        String title,
        String university,
        CountryId countryId) {

    public RegisterProfessionalStudyCommand {
        Objects.requireNonNull(studyId, "studyId must not be null");
        Objects.requireNonNull(professionalId, "professionalId must not be null");
        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(university, "university must not be null");
        Objects.requireNonNull(countryId, "countryId must not be null");
    }
}