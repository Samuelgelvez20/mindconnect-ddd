package com.mindconnect.application.chat.chatmessage.usecase;

import com.mindconnect.application.chat.chatmessage.command.RegisterChatMessageCommand;
import com.mindconnect.application.chat.chatmessage.dto.ChatMessageResponse;
import com.mindconnect.domain.chat.chatmessage.model.aggregate.ChatMessage;
import com.mindconnect.domain.chat.chatmessage.port.repository.ChatMessageRepository;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.messagetype.model.valueobject.MessageTypeId;
import com.mindconnect.domain.chat.chatparticipant.model.valueobject.ChatParticipantId;

public class RegisterChatMessageUseCase {

    private final ChatMessageRepository messageRepository;

    public RegisterChatMessageUseCase(ChatMessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public ChatMessageResponse execute(RegisterChatMessageCommand command) {

        ChatMessage message = ChatMessage.register(
                command.conversationId(),
                command.messageTypeId(),
                command.participantId(),
                command.content(),
                command.metadata());

        return ChatMessageResponse.from(messageRepository.save(message));
    }
}