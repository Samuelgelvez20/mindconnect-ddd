package com.mindconnect.application.ai.chatairun.usecase;

import java.util.UUID;

import com.mindconnect.application.ai.chatairun.command.UpdateChatAiRunCommand;
import com.mindconnect.application.ai.chatairun.dto.ChatAiRunResponse;
import com.mindconnect.application.ai.chatairun.exception.ChatAiRunNotFoundApplicationException;
import com.mindconnect.domain.ai.chatairun.model.aggregate.ChatAiRun;
import com.mindconnect.domain.ai.chatairun.model.valueobject.ChatAiRunId;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.chatmessage.model.valueobject.ChatMessageId;
import com.mindconnect.domain.ai.aimodel.model.valueobject.AiModelId;
import com.mindconnect.domain.ai.chatairunstatus.model.valueobject.ChatAiRunStatusId;
import com.mindconnect.domain.ai.chatairun.port.repository.ChatAiRunRepository;

public class UpdateChatAiRunUseCase {

    private final ChatAiRunRepository repository;

    public UpdateChatAiRunUseCase(ChatAiRunRepository repository) {
        this.repository = repository;
    }

    public ChatAiRunResponse execute(UpdateChatAiRunCommand command) {
        ChatAiRunId runId = new ChatAiRunId(command.id());
        ChatAiRun run = repository.findById(runId)
                .orElseThrow(() -> new ChatAiRunNotFoundApplicationException(command.id().toString()));

        ChatConversationId conversationId = new ChatConversationId(command.conversationId());
        ChatMessageId messageId = new ChatMessageId(command.messageId());
        AiModelId modelId = new AiModelId(command.modelId());
        ChatAiRunStatusId aiRunStatusId = new ChatAiRunStatusId(command.aiRunStatusId());

        run.update(conversationId, messageId, modelId, aiRunStatusId);

        ChatAiRun saved = repository.save(run);
        return ChatAiRunResponse.from(saved);
    }
}