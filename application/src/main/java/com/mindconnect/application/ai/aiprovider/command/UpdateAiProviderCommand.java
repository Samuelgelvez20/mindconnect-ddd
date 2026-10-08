package com.mindconnect.application.ai.aiprovider.command;

import java.util.UUID;

public record UpdateAiProviderCommand(
        UUID id,
        String name,
        String legalName,
        String website,
        boolean active) {
}