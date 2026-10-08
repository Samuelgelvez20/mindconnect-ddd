package com.mindconnect.application.clinicalcatalog.diagnosticsystem.command;

public record UpdateDiagnosticSystemCommand(String code, String name, String version, Boolean active) {
}