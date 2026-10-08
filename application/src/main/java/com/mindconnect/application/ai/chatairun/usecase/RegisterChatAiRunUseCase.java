package com.mindconnect.application.ai.chatairun.usecase;

import java.util.UUID;

import com.mindconnect.application.ai.chatairun.command.RegisterChatAiRunCommand;
import com.mindconnect.application.ai.chatairun.dto.ChatAiRunResponse;
import com.mindconnect.domain.ai.chatairun.model.aggregate.ChatAiRun;
import com.mindconnect.domain.ai.chatairun.model.valueobject.ChatAiRunId;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.chatmessage.model.valueobject.ChatMessageId;
import com.mindconnect.domain.ai.aimodel.model.valueobject.AiModelId;
import com.mindconnect.domain.ai.chatairunstatus.model.valueobject.ChatAiRunStatusId;
import com.mindconnect.domain.ai.chatairun.port.repository.ChatAiRunRepository;

public class RegisterChatAiRunUseCase {

    private final ChatAiRunRepository repository;

    public RegisterChatAiRunUseCase(ChatAiRunRepository repository) {
        this.repository = repository;
    }

    public ChatAiRunResponse execute(RegisterChatAiRunCommand command) {
        ChatConversationId conversationId = new ChatConversationId(command.conversationId());
        ChatMessageId messageId = new ChatMessageId(command.messageId());
        AiModelId modelId = new AiModelId(command.modelId());
        ChatAiRunStatusId aiRunStatusId = new ChatAiRunStatusId(command.aiRunStatusId());

        ChatAiRun run = ChatAiRun.register(conversationId, messageId, modelId, aiRunStatusId);

        ChatAiRun saved = repository.save(run);
        return ChatAiRunResponse.from(saved);
    }
}