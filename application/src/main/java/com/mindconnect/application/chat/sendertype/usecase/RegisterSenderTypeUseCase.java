package com.mindconnect.application.chat.sendertype.usecase;

import com.mindconnect.application.chat.sendertype.command.RegisterSenderTypeCommand;
import com.mindconnect.application.chat.sendertype.dto.SenderTypeResponse;
import com.mindconnect.application.chat.sendertype.exception.SenderTypeAlreadyExistsApplicationException;
import com.mindconnect.domain.chat.sendertype.model.aggregate.SenderType;
import com.mindconnect.domain.chat.sendertype.port.repository.SenderTypeRepository;

public class RegisterSenderTypeUseCase {

    private final SenderTypeRepository senderTypeRepository;

    public RegisterSenderTypeUseCase(SenderTypeRepository senderTypeRepository) {
        this.senderTypeRepository = senderTypeRepository;
    }

    public SenderTypeResponse execute(RegisterSenderTypeCommand command) {

        SenderType senderType = SenderType.register(command.name());

        if (senderTypeRepository.existsByName(senderType.name())) {
            throw new SenderTypeAlreadyExistsApplicationException(senderType.name());
        }

        return SenderTypeResponse.from(senderTypeRepository.save(senderType));
    }
}