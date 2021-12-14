package com.sdzl.openapi.sdzlopenapi.model.treatmentsummary;

import com.sdzl.openapi.sdzlopenapi.util.NumberUtil;
import java.util.Date;
import lombok.Data;

@Data
public class DoseSummaryDto {
    private String siteId;
    private String siteName;
    private Double plannedDose;
    private Double deliveredDose;
    private Double remainingDose;
    private Double dosePerFraction;
    private Date lastTreatmentTime;

    public void setPlannedDose(Double plannedDose){
        this.plannedDose = NumberUtil.threeDecimalPlaceRoundOff(plannedDose);
    }

    public void setDeliveredDose(Double deliveredDose) {
        this.deliveredDose = NumberUtil.threeDecimalPlaceRoundOff(deliveredDose);
    }

    public void setRemainingDose(Double remainingDose) {
        this.remainingDose = NumberUtil.threeDecimalPlaceRoundOff(remainingDose);
    }

    public void setDosePerFraction(Double dosePerFraction) {
        this.dosePerFraction = NumberUtil.threeDecimalPlaceRoundOff(dosePerFraction);
    }
}
