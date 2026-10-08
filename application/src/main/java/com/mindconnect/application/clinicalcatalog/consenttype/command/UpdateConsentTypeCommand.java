package com.mindconnect.application.clinicalcatalog.consenttype.command;

public record UpdateConsentTypeCommand(String code, String name, String description, Boolean active) {
}