package com.mindconnect.infrastructure.clinicalrecord.mentalstatusexam.adapters.out.persistence.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "mental_status_exams", schema = "mindconnect_schema")
public class MentalStatusExamJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "encounter_id", nullable = false)
    private UUID encounterId;

    @Column(name = "appearance")
    private String appearance;

    @Column(name = "behavior")
    private String behavior;

    @Column(name = "attitude")
    private String attitude;

    @Column(name = "consciousness")
    private String consciousness;

    @Column(name = "orientation")
    private String orientation;

    @Column(name = "attention")
    private String attention;

    @Column(name = "memory")
    private String memory;

    @Column(name = "speech")
    private String speech;

    @Column(name = "mood")
    private String mood;

    @Column(name = "affect")
    private String affect;

    @Column(name = "thought_process")
    private String thoughtProcess;

    @Column(name = "thought_content")
    private String thoughtContent;

    @Column(name = "perception")
    private String perception;

    @Column(name = "judgment")
    private String judgment;

    @Column(name = "insight")
    private String insight;

    @Column(name = "psychomotor_activity")
    private String psychomotorActivity;

    @Column(name = "observations")
    private String observations;

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public MentalStatusExamJpaEntity() {
    }

    public MentalStatusExamJpaEntity(
            UUID id, UUID encounterId, String appearance, String behavior, String attitude,
            String consciousness, String orientation, String attention, String memory,
            String speech, String mood, String affect, String thoughtProcess, String thoughtContent,
            String perception, String judgment, String insight, String psychomotorActivity,
            String observations, UUID createdBy, Instant createdAt) {

        this.id = id;
        this.encounterId = encounterId;
        this.appearance = appearance;
        this.behavior = behavior;
        this.attitude = attitude;
        this.consciousness = consciousness;
        this.orientation = orientation;
        this.attention = attention;
        this.memory = memory;
        this.speech = speech;
        this.mood = mood;
        this.affect = affect;
        this.thoughtProcess = thoughtProcess;
        this.thoughtContent = thoughtContent;
        this.perception = perception;
        this.judgment = judgment;
        this.insight = insight;
        this.psychomotorActivity = psychomotorActivity;
        this.observations = observations;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(UUID encounterId) {
        this.encounterId = encounterId;
    }

    public String getAppearance() {
        return appearance;
    }

    public void setAppearance(String appearance) {
        this.appearance = appearance;
    }

    public String getBehavior() {
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }

    public String getAttitude() {
        return attitude;
    }

    public void setAttitude(String attitude) {
        this.attitude = attitude;
    }

    public String getConsciousness() {
        return consciousness;
    }

    public void setConsciousness(String consciousness) {
        this.consciousness = consciousness;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getSpeech() {
        return speech;
    }

    public void setSpeech(String speech) {
        this.speech = speech;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getAffect() {
        return affect;
    }

    public void setAffect(String affect) {
        this.affect = affect;
    }

    public String getThoughtProcess() {
        return thoughtProcess;
    }

    public void setThoughtProcess(String thoughtProcess) {
        this.thoughtProcess = thoughtProcess;
    }

    public String getThoughtContent() {
        return thoughtContent;
    }

    public void setThoughtContent(String thoughtContent) {
        this.thoughtContent = thoughtContent;
    }

    public String getPerception() {
        return perception;
    }

    public void setPerception(String perception) {
        this.perception = perception;
    }

    public String getJudgment() {
        return judgment;
    }

    public void setJudgment(String judgment) {
        this.judgment = judgment;
    }

    public String getInsight() {
        return insight;
    }

    public void setInsight(String insight) {
        this.insight = insight;
    }

    public String getPsychomotorActivity() {
        return psychomotorActivity;
    }

    public void setPsychomotorActivity(String psychomotorActivity) {
        this.psychomotorActivity = psychomotorActivity;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}