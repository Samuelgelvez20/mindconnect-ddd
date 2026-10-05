package com.mindconnect.application.professional.professional.usecase;

import java.util.List;

import com.mindconnect.application.professional.professional.dto.ProfessionalResponse;
import com.mindconnect.domain.professional.professional.port.repository.ProfessionalRepository;

public class ListProfessionalUseCase {

    private final ProfessionalRepository professionalRepository;

    public ListProfessionalUseCase(ProfessionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }

    public List<ProfessionalResponse> execute() {
        return professionalRepository.findAll()
                .stream()
                .map(ProfessionalResponse::from)
                .toList();
    }
}