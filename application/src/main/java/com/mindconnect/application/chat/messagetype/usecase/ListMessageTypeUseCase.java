package com.mindconnect.application.chat.messagetype.usecase;

import java.util.List;

import com.mindconnect.application.chat.messagetype.dto.MessageTypeResponse;
import com.mindconnect.domain.chat.messagetype.port.repository.MessageTypeRepository;

public class ListMessageTypeUseCase {

    private final MessageTypeRepository messageTypeRepository;

    public ListMessageTypeUseCase(MessageTypeRepository messageTypeRepository) {
        this.messageTypeRepository = messageTypeRepository;
    }

    public List<MessageTypeResponse> execute() {
        return messageTypeRepository.findAll()
                .stream()
                .map(MessageTypeResponse::from)
                .toList();
    }
}