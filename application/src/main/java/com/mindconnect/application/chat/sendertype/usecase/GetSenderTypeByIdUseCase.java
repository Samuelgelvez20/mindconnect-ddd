package com.mindconnect.application.chat.sendertype.usecase;

import com.mindconnect.application.chat.sendertype.dto.SenderTypeResponse;
import com.mindconnect.application.chat.sendertype.exception.SenderTypeNotFoundApplicationException;
import com.mindconnect.domain.chat.sendertype.model.valueobject.SenderTypeId;
import com.mindconnect.domain.chat.sendertype.port.repository.SenderTypeRepository;

public class GetSenderTypeByIdUseCase {

    private final SenderTypeRepository senderTypeRepository;

    public GetSenderTypeByIdUseCase(SenderTypeRepository senderTypeRepository) {
        this.senderTypeRepository = senderTypeRepository;
    }

    public SenderTypeResponse execute(SenderTypeId id) {
        return senderTypeRepository.findById(id)
                .map(SenderTypeResponse::from)
                .orElseThrow(() -> new SenderTypeNotFoundApplicationException(id));
    }
}