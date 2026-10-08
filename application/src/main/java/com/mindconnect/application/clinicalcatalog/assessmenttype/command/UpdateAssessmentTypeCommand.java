package com.mindconnect.application.clinicalcatalog.assessmenttype.command;

public record UpdateAssessmentTypeCommand(String code, String name, String description, Boolean active) {
}