package com.mindconnect.infrastructure.professional.professionalstudy.adapters.out.persistence.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/** Mirrors table professional_studies (migration V15) exactly. */
@Entity
@Table(name = "professional_studies")
public class ProfessionalStudyJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "study_id", nullable = false)
    private UUID studyId;

    @Column(name = "professional_id", nullable = false)
    private UUID professionalId;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "university", nullable = false, length = 100)
    private String university;

    @Column(name = "is_valid", nullable = false)
    private boolean isValid;

    @Column(name = "resolution_number", length = 60)
    private String resolutionNumber;

    @Column(name = "country_id", nullable = false)
    private UUID countryId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public ProfessionalStudyJpaEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getStudyId() {
        return studyId;
    }

    public void setStudyId(UUID studyId) {
        this.studyId = studyId;
    }

    public UUID getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(UUID professionalId) {
        this.professionalId = professionalId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getResolutionNumber() {
        return resolutionNumber;
    }

    public void setResolutionNumber(String resolutionNumber) {
        this.resolutionNumber = resolutionNumber;
    }

    public UUID getCountryId() {
        return countryId;
    }

    public void setCountryId(UUID countryId) {
        this.countryId = countryId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}