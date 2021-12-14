package com.sdzl.openapi.sdzlopenapi.model.treatmentsummary;

import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class TreatmentSummaryDto {
    private Date lastTreatmentDate;
    private String doseUnit;
    private List<PlanSummaryDto> plans;
}
