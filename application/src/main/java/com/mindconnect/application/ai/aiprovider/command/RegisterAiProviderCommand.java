package com.mindconnect.application.ai.aiprovider.command;

import java.util.UUID;

public record RegisterAiProviderCommand(
        String name,
        String legalName,
        String website) {
}