package com.mindconnect.application.professional.professionaltype.usecase;

import java.util.List;

import com.mindconnect.application.professional.professionaltype.dto.ProfessionalTypeResponse;
import com.mindconnect.domain.professional.professionaltype.port.repository.ProfessionalTypeRepository;

public class ListProfessionalTypeUseCase {

    private final ProfessionalTypeRepository professionalTypeRepository;

    public ListProfessionalTypeUseCase(ProfessionalTypeRepository professionalTypeRepository) {
        this.professionalTypeRepository = professionalTypeRepository;
    }

    public List<ProfessionalTypeResponse> execute() {
        return professionalTypeRepository.findAll()
                .stream()
                .map(ProfessionalTypeResponse::from)
                .toList();
    }
}