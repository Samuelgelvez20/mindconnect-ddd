package com.mindconnect.application.clinicalrecord.mentalstatusexam.usecase;

import com.mindconnect.application.clinicalrecord.mentalstatusexam.command.RegisterMentalStatusExamCommand;
import com.mindconnect.application.clinicalrecord.mentalstatusexam.dto.MentalStatusExamResponse;
import com.mindconnect.application.clinicalrecord.mentalstatusexam.exception.MentalStatusExamNotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.mentalstatusexam.model.aggregate.MentalStatusExam;
import com.mindconnect.domain.clinicalrecord.mentalstatusexam.port.repository.MentalStatusExamRepository;

public class RegisterMentalStatusExamUseCase {

    private final MentalStatusExamRepository repository;

    public RegisterMentalStatusExamUseCase(MentalStatusExamRepository repository) {
        this.repository = repository;
    }

    public MentalStatusExamResponse execute(RegisterMentalStatusExamCommand command) {

        MentalStatusExam exam = MentalStatusExam.register(
                command.encounterId(),
                command.appearance(),
                command.behavior(),
                command.attitude(),
                command.consciousness(),
                command.orientation(),
                command.attention(),
                command.memory(),
                command.speech(),
                command.mood(),
                command.affect(),
                command.thoughtProcess(),
                command.thoughtContent(),
                command.perception(),
                command.judgment(),
                command.insight(),
                command.psychomotorActivity(),
                command.observations(),
                command.createdBy());

        return MentalStatusExamResponse.from(repository.save(exam));
    }
}