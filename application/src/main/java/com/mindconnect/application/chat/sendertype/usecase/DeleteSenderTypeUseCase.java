package com.mindconnect.application.chat.sendertype.usecase;

import com.mindconnect.application.chat.sendertype.exception.SenderTypeNotFoundApplicationException;
import com.mindconnect.domain.chat.sendertype.model.valueobject.SenderTypeId;
import com.mindconnect.domain.chat.sendertype.port.repository.SenderTypeRepository;

public class DeleteSenderTypeUseCase {

    private final SenderTypeRepository senderTypeRepository;

    public DeleteSenderTypeUseCase(SenderTypeRepository senderTypeRepository) {
        this.senderTypeRepository = senderTypeRepository;
    }

    public void execute(SenderTypeId id) {

        var senderType = senderTypeRepository.findById(id)
                .orElseThrow(() -> new SenderTypeNotFoundApplicationException(id));

        senderType.delete();
        senderTypeRepository.delete(senderType);
    }
}