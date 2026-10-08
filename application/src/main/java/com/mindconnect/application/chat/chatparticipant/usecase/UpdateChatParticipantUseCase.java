package com.mindconnect.application.chat.chatparticipant.usecase;

import com.mindconnect.application.chat.chatparticipant.command.UpdateChatParticipantCommand;
import com.mindconnect.application.chat.chatparticipant.dto.ChatParticipantResponse;
import com.mindconnect.application.chat.chatparticipant.exception.ChatParticipantNotFoundApplicationException;
import com.mindconnect.domain.chat.chatparticipant.model.valueobject.ChatParticipantId;
import com.mindconnect.domain.chat.chatparticipant.port.repository.ChatParticipantRepository;
import com.mindconnect.domain.chat.chatconversation.model.valueobject.ChatConversationId;
import com.mindconnect.domain.chat.sendertype.model.valueobject.SenderTypeId;
import com.mindconnect.domain.patient.patient.model.valueobject.PatientId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;

public class UpdateChatParticipantUseCase {

    private final ChatParticipantRepository participantRepository;

    public UpdateChatParticipantUseCase(ChatParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public ChatParticipantResponse execute(UpdateChatParticipantCommand command) {

        var participant = participantRepository.findById(command.id())
                .orElseThrow(() -> new ChatParticipantNotFoundApplicationException(command.id()));

        participant.update(
                command.conversationId(),
                command.participantTypeId(),
                command.patientId(),
                command.professionalId());

        return ChatParticipantResponse.from(participantRepository.save(participant));
    }
}