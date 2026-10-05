package com.mindconnect.application.treatment.treatmentplan.usecase;

import com.mindconnect.application.treatment.treatmentplan.dto.TreatmentPlanResponse;
import com.mindconnect.domain.treatment.treatmentplan.model.aggregate.TreatmentPlan;
import com.mindconnect.domain.treatment.treatmentplan.model.valueobject.TreatmentPlanId;
import com.mindconnect.domain.clinicalrecord.encounter.model.valueobject.EncounterId;
import com.mindconnect.domain.professional.professional.model.valueobject.ProfessionalId;
import com.mindconnect.domain.treatment.treatmentstatus.model.valueobject.TreatmentStatusId;
import com.mindconnect.application.treatment.treatmentplan.command.RegisterTreatmentPlanCommand;
import com.mindconnect.application.treatment.treatmentplan.exception.TreatmentPlanAlreadyExistsApplicationException;
import com.mindconnect.domain.treatment.treatmentplan.port.repository.TreatmentPlanRepository;

public class RegisterTreatmentPlanUseCase {

    private final TreatmentPlanRepository repository;

    public RegisterTreatmentPlanUseCase(TreatmentPlanRepository repository) {
        this.repository = repository;
    }

    public TreatmentPlanResponse execute(RegisterTreatmentPlanCommand command) {

        TreatmentPlanId id = TreatmentPlanId.generate();

        TreatmentPlan existing = repository.findById(id)
                .filter(t -> t.title().equals(command.title()))
                .orElse(null);

        if (existing != null) {
            throw new TreatmentPlanAlreadyExistsApplicationException("TreatmentPlan with title " + command.title() + " already exists");
        }

        EncounterId encounterId = new EncounterId(java.util.UUID.fromString(command.encounterId()));
        ProfessionalId professionalId = new ProfessionalId(java.util.UUID.fromString(command.professionalId()));
        TreatmentStatusId treatmentStatusId = new TreatmentStatusId(java.util.UUID.fromString(command.treatmentStatusId()));

        TreatmentPlan plan = TreatmentPlan.register(encounterId, professionalId, command.title(), command.description(),
                java.time.LocalDate.parse(command.startDate()), java.time.LocalDate.parse(command.endDate()), treatmentStatusId);
        repository.save(plan);

        return TreatmentPlanResponse.from(plan);
    }
}