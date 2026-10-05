package com.mindconnect.domain.patient.patient.model.aggregate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.mindconnect.domain.common.model.AggregateRoot;
import com.mindconnect.domain.patient.patient.event.PatientDeletedEvent;
import com.mindconnect.domain.patient.patient.event.PatientRegisteredEvent;
import com.mindconnect.domain.patient.patient.event.PatientUpdatedEvent;
import com.mindconnect.domain.patient.patient.exception.InvalidPatientException;
import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.referencedata.citymunicipality.model.valueobject.CityMunicipalityId;
import com.mindconnect.domain.referencedata.documenttype.model.valueobject.DocumentTypeId;
import com.mindconnect.domain.referencedata.gender.model.valueobject.GenderId;

public class Patient extends AggregateRoot {

    public static final int DOCUMENT_NUMBER_MAX_LENGTH = 30;
    public static final int FIRST_NAME_MAX_LENGTH = 50;
    public static final int MIDDLE_NAME_MAX_LENGTH = 50;
    public static final int LAST_NAME_MAX_LENGTH = 50;
    public static final int SECOND_LAST_NAME_MAX_LENGTH = 50;
    public static final int EMAIL_MAX_LENGTH = 150;
    public static final int PHONE_MAX_LENGTH = 30;
    public static final int ADDRESS_MAX_LENGTH = 250;

    private final PatientId id;
    private DocumentTypeId documentTypeId;
    private String documentNumber;
    private String firstName;
    private String middleName;
    private String lastName;
    private String secondLastName;
    private LocalDate birthDate;
    private GenderId biologicalSexId;
    private GenderId genderIdentityId;
    private String email;
    private String phone;
    private String address;
    private boolean active;
    private ProfessionalId createdBy;
    private ProfessionalId updatedBy;
    private CityMunicipalityId cityId;
    private final Instant createdAt;
    private Instant updatedAt;

    private Patient(
            PatientId id,
            DocumentTypeId documentTypeId,
            String documentNumber,
            String firstName,
            String middleName,
            String lastName,
            String secondLastName,
            LocalDate birthDate,
            GenderId biologicalSexId,
            GenderId genderIdentityId,
            String email,
            String phone,
            String address,
            boolean active,
            ProfessionalId createdBy,
            ProfessionalId updatedBy,
            CityMunicipalityId cityId,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.documentTypeId = documentTypeId;
        this.documentNumber = documentNumber;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
        this.birthDate = birthDate;
        this.biologicalSexId = biologicalSexId;
        this.genderIdentityId = genderIdentityId;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.active = active;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.cityId = cityId;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static Patient register(
            DocumentTypeId documentTypeId,
            String documentNumber,
            String firstName,
            String middleName,
            String lastName,
            String secondLastName,
            LocalDate birthDate,
            GenderId biologicalSexId,
            GenderId genderIdentityId,
            String email,
            String phone,
            String address,
            ProfessionalId createdBy,
            CityMunicipalityId cityId) {

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        PatientId id = PatientId.generate();

        // Validate required fields
        if (documentTypeId == null) {
            throw new InvalidPatientException("documentTypeId must not be null");
        }
        if (birthDate == null) {
            throw new InvalidPatientException("birthDate must not be null");
        }
        if (biologicalSexId == null) {
            throw new InvalidPatientException("biologicalSexId must not be null");
        }
        if (genderIdentityId == null) {
            throw new InvalidPatientException("genderIdentityId must not be null");
        }
        if (cityId == null) {
            throw new InvalidPatientException("cityId must not be null");
        }
        if (createdBy == null) {
            throw new InvalidPatientException("createdBy must not be null");
        }

        Patient patient = new Patient(
                id,
                documentTypeId,
                requiredText(documentNumber, "documentNumber", DOCUMENT_NUMBER_MAX_LENGTH),
                requiredText(firstName, "firstName", FIRST_NAME_MAX_LENGTH),
                optionalText(middleName, "middleName", MIDDLE_NAME_MAX_LENGTH),
                requiredText(lastName, "lastName", LAST_NAME_MAX_LENGTH),
                optionalText(secondLastName, "secondLastName", SECOND_LAST_NAME_MAX_LENGTH),
                birthDate,
                biologicalSexId,
                genderIdentityId,
                requiredText(email, "email", EMAIL_MAX_LENGTH),
                optionalText(phone, "phone", PHONE_MAX_LENGTH),
                optionalText(address, "address", ADDRESS_MAX_LENGTH),
                true,
                createdBy,
                null,
                cityId,
                now,
                now);

        patient.recordEvent(new PatientRegisteredEvent(id, now));
        return patient;
    }

    public static Patient restore(
            PatientId id,
            DocumentTypeId documentTypeId,
            String documentNumber,
            String firstName,
            String middleName,
            String lastName,
            String secondLastName,
            LocalDate birthDate,
            GenderId biologicalSexId,
            GenderId genderIdentityId,
            String email,
            String phone,
            String address,
            boolean active,
            ProfessionalId createdBy,
            ProfessionalId updatedBy,
            CityMunicipalityId cityId,
            Instant createdAt,
            Instant updatedAt) {

        return new Patient(id, documentTypeId, documentNumber, firstName, middleName, lastName,
                secondLastName, birthDate, biologicalSexId, genderIdentityId, email, phone, address,
                active, createdBy, updatedBy, cityId, createdAt, updatedAt);
    }

    public void update(
            DocumentTypeId documentTypeId,
            String documentNumber,
            String firstName,
            String middleName,
            String lastName,
            String secondLastName,
            LocalDate birthDate,
            GenderId biologicalSexId,
            GenderId genderIdentityId,
            String email,
            String phone,
            String address,
            boolean active,
            ProfessionalId updatedBy,
            CityMunicipalityId cityId) {

        // Validate required fields
        if (documentTypeId == null) {
            throw new InvalidPatientException("documentTypeId must not be null");
        }
        if (birthDate == null) {
            throw new InvalidPatientException("birthDate must not be null");
        }
        if (biologicalSexId == null) {
            throw new InvalidPatientException("biologicalSexId must not be null");
        }
        if (genderIdentityId == null) {
            throw new InvalidPatientException("genderIdentityId must not be null");
        }
        if (cityId == null) {
            throw new InvalidPatientException("cityId must not be null");
        }
        if (updatedBy == null) {
            throw new InvalidPatientException("updatedBy must not be null");
        }

        this.documentTypeId = documentTypeId;
        this.documentNumber = requiredText(documentNumber, "documentNumber", DOCUMENT_NUMBER_MAX_LENGTH);
        this.firstName = requiredText(firstName, "firstName", FIRST_NAME_MAX_LENGTH);
        this.middleName = optionalText(middleName, "middleName", MIDDLE_NAME_MAX_LENGTH);
        this.lastName = requiredText(lastName, "lastName", LAST_NAME_MAX_LENGTH);
        this.secondLastName = optionalText(secondLastName, "secondLastName", SECOND_LAST_NAME_MAX_LENGTH);
        this.birthDate = birthDate;
        this.biologicalSexId = biologicalSexId;
        this.genderIdentityId = genderIdentityId;
        this.email = requiredText(email, "email", EMAIL_MAX_LENGTH);
        this.phone = optionalText(phone, "phone", PHONE_MAX_LENGTH);
        this.address = optionalText(address, "address", ADDRESS_MAX_LENGTH);
        this.active = active;
        this.updatedBy = updatedBy;
        this.cityId = cityId;
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        recordEvent(new PatientUpdatedEvent(this.id, this.updatedAt));
    }

    public void delete() {
        recordEvent(new PatientDeletedEvent(this.id, Instant.now().truncatedTo(ChronoUnit.MICROS)));
    }

    public PatientId id() {
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

    public String middleName() {
        return middleName;
    }

    public String lastName() {
        return lastName;
    }

    public String secondLastName() {
        return secondLastName;
    }

    public LocalDate birthDate() {
        return birthDate;
    }

    public GenderId biologicalSexId() {
        return biologicalSexId;
    }

    public GenderId genderIdentityId() {
        return genderIdentityId;
    }

    public String email() {
        return email;
    }

    public String phone() {
        return phone;
    }

    public String address() {
        return address;
    }

    public boolean active() {
        return active;
    }

    public ProfessionalId createdBy() {
        return createdBy;
    }

    public ProfessionalId updatedBy() {
        return updatedBy;
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
            throw new InvalidPatientException(field + " must not be blank");
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidPatientException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }

    private static String optionalText(String value, String field, int maxLength) {
        if (value == null || value.isBlank()) {
            return null;
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new InvalidPatientException(field + " must have at most " + maxLength + " characters");
        }
        return trimmed;
    }
}