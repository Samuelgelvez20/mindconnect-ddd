package com.mindconnect.application.chat.priority.usecase;

import com.mindconnect.application.chat.priority.command.RegisterPriorityCommand;
import com.mindconnect.application.chat.priority.dto.PriorityResponse;
import com.mindconnect.application.chat.priority.exception.PriorityAlreadyExistsApplicationException;
import com.mindconnect.domain.chat.priority.model.aggregate.Priority;
import com.mindconnect.domain.chat.priority.port.repository.PriorityRepository;

public class RegisterPriorityUseCase {

    private final PriorityRepository priorityRepository;

    public RegisterPriorityUseCase(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    public PriorityResponse execute(RegisterPriorityCommand command) {

        Priority priority = Priority.register(command.name());

        if (priorityRepository.existsByName(priority.name())) {
            throw new PriorityAlreadyExistsApplicationException(priority.name());
        }

        return PriorityResponse.from(priorityRepository.save(priority));
    }
}