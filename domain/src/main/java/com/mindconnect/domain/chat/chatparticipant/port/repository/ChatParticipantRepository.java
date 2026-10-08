package com.mindconnect.domain.chat.chatparticipant.port.repository;

import java.util.List;
import java.util.Optional;

import com.mindconnect.domain.chat.chatparticipant.model.aggregate.ChatParticipant;
import com.mindconnect.domain.chat.chatparticipant.model.valueobject.ChatParticipantId;

public interface ChatParticipantRepository {

    ChatParticipant save(ChatParticipant chatParticipant);

    Optional<ChatParticipant> findById(ChatParticipantId id);

    List<ChatParticipant> findAll();

    void delete(ChatParticipant chatParticipant);
}