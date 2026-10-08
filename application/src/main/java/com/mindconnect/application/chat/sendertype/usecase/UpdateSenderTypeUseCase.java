package com.mindconnect.application.chat.sendertype.usecase;

import com.mindconnect.application.chat.sendertype.command.UpdateSenderTypeCommand;
import com.mindconnect.application.chat.sendertype.dto.SenderTypeResponse;
import com.mindconnect.application.chat.sendertype.exception.SenderTypeAlreadyExistsApplicationException;
import com.mindconnect.application.chat.sendertype.exception.SenderTypeNotFoundApplicationException;
import com.mindconnect.domain.chat.sendertype.model.valueobject.SenderTypeId;
import com.mindconnect.domain.chat.sendertype.port.repository.SenderTypeRepository;

public class UpdateSenderTypeUseCase {

    private final SenderTypeRepository senderTypeRepository;

    public UpdateSenderTypeUseCase(SenderTypeRepository senderTypeRepository) {
        this.senderTypeRepository = senderTypeRepository;
    }

    public SenderTypeResponse execute(UpdateSenderTypeCommand command) {

        var senderType = senderTypeRepository.findById(command.id())
                .orElseThrow(() -> new SenderTypeNotFoundApplicationException(command.id()));

        senderType.update(command.name());

        if (senderTypeRepository.existsByNameAndIdNot(senderType.name(), senderType.id())) {
            throw new SenderTypeAlreadyExistsApplicationException(senderType.name());
        }

        return SenderTypeResponse.from(senderTypeRepository.save(senderType));
    }
}