package com.mindconnect.application.chat.chatmessage.usecase;

import com.mindconnect.application.chat.chatmessage.command.UpdateChatMessageCommand;
import com.mindconnect.application.chat.chatmessage.dto.ChatMessageResponse;
import com.mindconnect.application.chat.chatmessage.exception.ChatMessageNotFoundApplicationException;
import com.mindconnect.domain.chat.chatmessage.model.valueobject.ChatMessageId;
import com.mindconnect.domain.chat.chatmessage.port.repository.ChatMessageRepository;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.messagetype.model.valueobject.MessageTypeId;
import com.mindconnect.domain.chat.chatparticipant.model.valueobject.ChatParticipantId;

public class UpdateChatMessageUseCase {

    private final ChatMessageRepository messageRepository;

    public UpdateChatMessageUseCase(ChatMessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public ChatMessageResponse execute(UpdateChatMessageCommand command) {

        var message = messageRepository.findById(command.id())
                .orElseThrow(() -> new ChatMessageNotFoundApplicationException(command.id()));

        message.update(
                command.conversationId(),
                command.messageTypeId(),
                command.participantId(),
                command.content(),
                command.metadata());

        return ChatMessageResponse.from(messageRepository.save(message));
    }
}