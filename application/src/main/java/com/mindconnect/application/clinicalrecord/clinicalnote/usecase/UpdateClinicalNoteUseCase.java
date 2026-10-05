package com.mindconnect.application.clinicalrecord.clinicalnote.usecase;

import com.mindconnect.application.clinicalrecord.clinicalnote.command.UpdateClinicalNoteCommand;
import com.mindconnect.application.clinicalrecord.clinicalnote.dto.ClinicalNoteResponse;
import com.mindconnect.application.clinicalrecord.clinicalnote.exception.ClinicalNoteNotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.clinicalnote.model.aggregate.ClinicalNote;
import com.mindconnect.domain.clinicalrecord.clinicalnote.port.repository.ClinicalNoteRepository;

public class UpdateClinicalNoteUseCase {

    private final ClinicalNoteRepository repository;

    public UpdateClinicalNoteUseCase(ClinicalNoteRepository repository) {
        this.repository = repository;
    }

    public ClinicalNoteResponse execute(UpdateClinicalNoteCommand command) {

        var note = repository.findById(command.id())
                .orElseThrow(() -> new ClinicalNoteNotFoundApplicationException(command.id()));

        note.update(
                command.encounterId(),
                command.professionalId(),
                command.subjective(),
                command.objective(),
                command.assessment(),
                command.plan(),
                command.additionalNotes(),
                command.signedAt());

        return ClinicalNoteResponse.from(repository.save(note));
    }
}