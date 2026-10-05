package com.mindconnect.domain.professional.professional.model.aggregate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.professional.professional.event.ProfessionalDeletedEvent;
import com.mindconnect.domain.professional.professional.event.ProfessionalRegisteredEvent;
import com.mindconnect.domain.professional.professional.event.ProfessionalUpdatedEvent;
import com.mindconnect.domain.professional.professional.exception.InvalidProfessionalException;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;
import com.mindconnect.domain.professional.professionaltype.model.valueobject.ProfessionalTypeId;

public class Professional extends AggregateRoot {

    public static final int DOCUMENT_NUMBER_MAX_LENGTH = 30;
    public static final int FIRST_NAME_MAX_LENGTH = 60;
    public static final int LAST_NAME_MAX_LENGTH = 60;
    public static final int LICENSE_NUMBER_MAX_LENGTH = 100;

    private final ProfessionalId id;
    private DocumentTypeId documentTypeId;
    private String documentNumber;
    private String firstName;
    private String lastName;
    private ProfessionalTypeId professionalTypeId;
    private String licenseNumber;
    private boolean active;
    private CityMunicipalityId cityId;
    private final Instant createdAt;
    private Instant updatedAt;

    private Professional(
            ProfessionalId id,
            DocumentTypeId documentTypeId,
            String documentNumber,
            String firstName,
            String lastName,
            ProfessionalTypeId professionalTypeId,
            String licenseNumber,
            boolean active,
            CityMunicipalityId cityId,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.documentTypeId = documentTypeId;
        this.documentNumber = documentNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.professionalTypeId = professionalTypeId;
        this.licenseNumber = licenseNumber;
        this.active = active;
        this.cityId = cityId;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static Professional register(
            DocumentTypeId documentTypeId,
            String documentNumber,
            String firstName,
            String lastName,
            ProfessionalTypeId professionalTypeId,
            String licenseNumber,
            CityMunicipalityId cityId) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        ProfessionalId id = ProfessionalId.generate();

        if (documentTypeId == null) {
            throw new InvalidProfessionalException("documentTypeId must not be null");
        }
        if (professionalTypeId == null) {
            throw new InvalidProfessionalException("professionalTypeId must not be null");
        }
        if (cityId == null) {
            throw new InvalidProfessionalException("cityId must not be null");
        }

        Professional professional = new Professional(
                id,
                documentTypeId,
                requiredText(documentNumber, "documentNumber", DOCUMENT_NUMBER_MAX_LENGTH),
                requiredText(firstName, "firstName", FIRST_NAME_MAX_LENGTH),
                requiredText(lastName, "lastName", LAST_NAME_MAX_LENGTH),
                professionalTypeId,
                requiredText(licenseNumber, "licenseNumber", LICENSE_NUMBER_MAX_LENGTH),
                true,
                cityId,
                now,
                now);

        professional.recordEvent(new ProfessionalRegisteredEvent(id, now));
        return professional;
    }

    public static Professional restore(
            ProfessionalId id,
            DocumentTypeId documentTypeId,
            String documentNumber,
            String firstName,
            String lastName,
            ProfessionalTypeId professionalTypeId,
            String licenseNumber,
            boolean active,
            CityMunicipalityId cityId,
            Instant createdAt,
            Instant updatedAt) {

        return new Professional(
                id,
                documentTypeId,
                documentNumber,
                firstName,
                lastName,
                professionalTypeId,
                licenseNumber,
                active,
                cityId,
                createdAt,
                updatedAt);
    }

    public void update(
            DocumentTypeId documentTypeId,
            String documentNumber,
            String firstName,
            String lastName,
            ProfessionalTypeId professionalTypeId,
            String licenseNumber,
            boolean active,
            CityMunicipalityId cityId) {

        this.documentTypeId = Objects.requireNonNull(documentTypeId, "documentTypeId must not be null");
        this.documentNumber = requiredText(documentNumber, "documentNumber", DOCUMENT_NUMBER_MAX_LENGTH);
        this.firstName = requiredText(firstName, "firstName", FIRST_NAME_MAX_LENGTH);
        this.lastName = requiredText(lastName, "lastName", LAST_NAME_MAX_LENGTH);
        this.professionalTypeId = Objects.requireNonNull(professionalTypeId, "professionalTypeId must not be null");
        this.licenseNumber = requiredText(licenseNumber, "licenseNumber", LICENSE_NUMBER_MAX_LENGTH);
        this.active = active;
        this.cityId = Objects.requireNonNull(cityId, "cityId must not be null");
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new ProfessionalUpdatedEvent(
                this.id,
                this.documentTypeId,
                this.documentNumber,
                this.firstName,
                this.lastName,
                this.professionalTypeId,
                this.licenseNumber,
                this.active,
                this.cityId,
                this.updatedAt));
    }

    public void delete() {
        recordEvent(new ProfessionalDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public ProfessionalId id() {
        return id;
    }

    public DocumentTypeId documentTypeId() {
        return documentTypeId;
    }

    public String documentNumber() {
        return documentNumber;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public ProfessionalTypeId professionalTypeId() {
        return professionalTypeId;
    }

    public String licenseNumber() {
        return licenseNumber;
    }

    public boolean active() {
        return active;
    }

    public CityMunicipalityId cityId() {
        return cityId;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    private static String requiredText(String value, String field, int maxLength) {
        if (value == null || value.isBlank()) {
            throw new InvalidProfessionalException(field + " must not be blank");
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidProfessionalException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }
}