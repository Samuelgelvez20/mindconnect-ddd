package com.mindconnect.application.clinicalrecord.clinicalnote.usecase;

import com.mindconnect.application.clinicalrecord.clinicalnote.exception.ClinicalNoteNotFoundApplicationException;
import com.mindconnect.domain.clinicalrecord.clinicalnote.model.valueobject.ClinicalNoteId;
import com.mindconnect.domain.clinicalrecord.clinicalnote.port.repository.ClinicalNoteRepository;

public class DeleteClinicalNoteUseCase {

    private final ClinicalNoteRepository repository;

    public DeleteClinicalNoteUseCase(ClinicalNoteRepository repository) {
        this.repository = repository;
    }

    public void execute(ClinicalNoteId id) {

        var note = repository.findById(id)
                .orElseThrow(() -> new ClinicalNoteNotFoundApplicationException(id));

        note.delete();
        repository.delete(note);
    }
}