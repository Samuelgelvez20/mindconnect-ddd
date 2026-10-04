package com.mindconnect.domain.common.event;

import java.time.Instant;

public interface DomainEvent {
    Instant occurredOn();
}
