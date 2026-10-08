package com.mindconnect.application.chat.chatparticipant.usecase;

import com.mindconnect.application.chat.chatparticipant.command.RegisterChatParticipantCommand;
import com.mindconnect.application.chat.chatparticipant.dto.ChatParticipantResponse;
import com.mindconnect.domain.chat.chatparticipant.model.aggregate.ChatParticipant;
import com.mindconnect.domain.chat.chatparticipant.port.repository.ChatParticipantRepository;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.sendertype.model.valueobject.SenderTypeId;
import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

public class RegisterChatParticipantUseCase {

    private final ChatParticipantRepository participantRepository;

    public RegisterChatParticipantUseCase(ChatParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public ChatParticipantResponse execute(RegisterChatParticipantCommand command) {

        ChatParticipant participant = ChatParticipant.register(
                command.conversationId(),
                command.participantTypeId(),
                command.patientId(),
                command.professionalId());

        return ChatParticipantResponse.from(participantRepository.save(participant));
    }
}