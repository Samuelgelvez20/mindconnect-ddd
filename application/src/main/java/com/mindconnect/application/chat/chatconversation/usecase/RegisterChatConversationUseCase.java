package com.mindconnect.application.chat.chatconversation.usecase;

import com.mindconnect.application.chat.chatconversation.command.RegisterChatConversationCommand;
import com.mindconnect.application.chat.chatconversation.dto.ChatConversationResponse;
import com.mindconnect.domain.chat.chatconversation.model.aggregate.ChatConversation;
import com.mindconnect.domain.chat.chatconversation.port.repository.ChatConversationRepository;
import com.mindconnect.domain.chat.chatconversationstatus.model.valueobject.ChatConversationStatusId;
import com.mindconnect.domain.chat.priority.model.valueobject.PriorityId;

public class RegisterChatConversationUseCase {

    private final ChatConversationRepository conversationRepository;

    public RegisterChatConversationUseCase(ChatConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public ChatConversationResponse execute(RegisterChatConversationCommand command) {

        ChatConversation conversation = ChatConversation.register(
                command.conversationStatusId(),
                command.priorityId());

        return ChatConversationResponse.from(conversationRepository.save(conversation));
    }
}