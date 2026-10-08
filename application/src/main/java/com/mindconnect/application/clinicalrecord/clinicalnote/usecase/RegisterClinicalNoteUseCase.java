package com.mindconnect.application.clinicalrecord.clinicalnote.usecase;

import com.mindconnect.application.clinicalrecord.clinicalnote.command.RegisterClinicalNoteCommand;
import com.mindconnect.application.clinicalrecord.clinicalnote.dto.ClinicalNoteResponse;
import com.mindconnect.application.clinicalrecord.clinicalnote.exception.ClinicalNoteNotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.clinicalnote.model.aggregate.ClinicalNote;
import com.mindconnect.domain.clinicalrecord.clinicalnote.port.repository.ClinicalNoteRepository;

public class RegisterClinicalNoteUseCase {

    private final ClinicalNoteRepository repository;

    public RegisterClinicalNoteUseCase(ClinicalNoteRepository repository) {
        this.repository = repository;
    }

    public ClinicalNoteResponse execute(RegisterClinicalNoteCommand command) {

        ClinicalNote note = ClinicalNote.register(
                command.encounterId(),
                command.professionalId(),
                command.subjective(),
                command.objective(),
                command.assessment(),
                command.plan(),
                command.additionalNotes());

        return ClinicalNoteResponse.from(repository.save(note));
    }
}