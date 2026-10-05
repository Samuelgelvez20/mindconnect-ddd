package com.mindconnect.domain.professional.professionalstudy.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.professional.professionalstudy.event.ProfessionalStudyDeletedEvent;
import com.mindconnect.domain.professional.professionalstudy.event.ProfessionalStudyRegisteredEvent;
import com.mindconnect.domain.professional.professionalstudy.event.ProfessionalStudyUpdatedEvent;
import com.mindconnect.domain.professional.professionalstudy.exception.InvalidProfessionalStudyException;
import com.mindconnect.domain.professional.professionalstudy.model.valueobject.ProfessionalStudyId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.professional.study.model.valueobject.StudyId;
import com.mindconnect.domain.referencedata.country.model.valueobject.CountryId;

public class ProfessionalStudy extends AggregateRoot {

    public static final int TITLE_MAX_LENGTH = 100;
    public static final int UNIVERSITY_MAX_LENGTH = 100;
    public static final int RESOLUTION_NUMBER_MAX_LENGTH = 60;

    private final ProfessionalStudyId id;
    private StudyId studyId;
    private ProfessionalId professionalId;
    private String title;
    private String university;
    private boolean isValid;
    private String resolutionNumber;
    private CountryId countryId;
    private final Instant createdAt;
    private Instant updatedAt;

    private ProfessionalStudy(
            ProfessionalStudyId id,
            StudyId studyId,
            ProfessionalId professionalId,
            String title,
            String university,
            boolean isValid,
            String resolutionNumber,
            CountryId countryId,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.studyId = studyId;
        this.professionalId = professionalId;
        this.title = title;
        this.university = university;
        this.isValid = isValid;
        this.resolutionNumber = resolutionNumber;
        this.countryId = countryId;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static ProfessionalStudy register(
            StudyId studyId,
            ProfessionalId professionalId,
            String title,
            String university,
            CountryId countryId) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        ProfessionalStudyId id = ProfessionalStudyId.generate();

        if (studyId == null) {
            throw new InvalidProfessionalStudyException("studyId must not be null");
        }
        if (professionalId == null) {
            throw new InvalidProfessionalStudyException("professionalId must not be null");
        }
        if (countryId == null) {
            throw new InvalidProfessionalStudyException("countryId must not be null");
        }

        ProfessionalStudy professionalStudy = new ProfessionalStudy(
                id,
                studyId,
                professionalId,
                requiredText(title, "title", TITLE_MAX_LENGTH),
                requiredText(university, "university", UNIVERSITY_MAX_LENGTH),
                false,
                null,
                countryId,
                now,
                now);

        professionalStudy.recordEvent(new ProfessionalStudyRegisteredEvent(id, now));
        return professionalStudy;
    }

    public static ProfessionalStudy restore(
            ProfessionalStudyId id,
            StudyId studyId,
            ProfessionalId professionalId,
            String title,
            String university,
            boolean isValid,
            String resolutionNumber,
            CountryId countryId,
            Instant createdAt,
            Instant updatedAt) {

        return new ProfessionalStudy(id, studyId, professionalId, title, university, isValid, resolutionNumber, countryId, createdAt, updatedAt);
    }

    public void update(
            String title,
            String university,
            boolean isValid,
            String resolutionNumber,
            CountryId countryId) {

        this.title = requiredText(title, "title", TITLE_MAX_LENGTH);
        this.university = requiredText(university, "university", UNIVERSITY_MAX_LENGTH);
        this.isValid = isValid;
        this.resolutionNumber = optionalText(resolutionNumber, "resolutionNumber", RESOLUTION_NUMBER_MAX_LENGTH);
        this.countryId = Objects.requireNonNull(countryId, "countryId must not be null");
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new ProfessionalStudyUpdatedEvent(this.id, this.title, this.university, this.updatedAt));
    }

    public void delete() {
        recordEvent(new ProfessionalStudyDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public ProfessionalStudyId id() {
        return id;
    }

    public StudyId studyId() {
        return studyId;
    }

    public ProfessionalId professionalId() {
        return professionalId;
    }

    public String title() {
        return title;
    }

    public String university() {
        return university;
    }

    public boolean isValid() {
        return isValid;
    }

    public String resolutionNumber() {
        return resolutionNumber;
    }

    public CountryId countryId() {
        return countryId;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    private static String requiredText(String value, String field, int maxLength) {
        if (value == null || value.isBlank()) {
            throw new InvalidProfessionalStudyException(field + " must not be blank");
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidProfessionalStudyException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }

    private static String optionalText(String value, String field, int maxLength) {
        if (value == null || value.isBlank()) {
            return null;
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidProfessionalStudyException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }
}