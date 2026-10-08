package com.mindconnect.application.professional.professional.usecase;

import com.mindconnect.application.professional.professional.exception.ProfessionalNotFoundApplicationException;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.professional.professional.port.repository.ProfessionalRepository;

public class DeleteProfessionalUseCase {

    private final ProfessionalRepository professionalRepository;

    public DeleteProfessionalUseCase(ProfessionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }

    public void execute(ProfessionalId id) {

        var professional = professionalRepository.findById(id)
                .orElseThrow(() -> new ProfessionalNotFoundApplicationException(id));

        professional.delete();
        professionalRepository.delete(professional);
    }
}