package com.mindconnect.application.clinicalcatalog.medicationroute.command;

public record UpdateMedicationRouteCommand(String code, String name, Boolean active) {
}