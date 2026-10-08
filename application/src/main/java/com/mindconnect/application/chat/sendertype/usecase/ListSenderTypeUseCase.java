package com.mindconnect.application.chat.sendertype.usecase;

import java.util.List;

import com.mindconnect.application.chat.sendertype.dto.SenderTypeResponse;
import com.mindconnect.domain.chat.sendertype.port.repository.SenderTypeRepository;

public class ListSenderTypeUseCase {

    private final SenderTypeRepository senderTypeRepository;

    public ListSenderTypeUseCase(SenderTypeRepository senderTypeRepository) {
        this.senderTypeRepository = senderTypeRepository;
    }

    public List<SenderTypeResponse> execute() {
        return senderTypeRepository.findAll()
                .stream()
                .map(SenderTypeResponse::from)
                .toList();
    }
}