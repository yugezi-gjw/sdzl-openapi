package com.sdzl.openapi.sdzlopenapi.model.treatmentsummary;

import com.sdzl.openapi.sdzlopenapi.util.NumberUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class PlanSummaryDto implements Comparable<PlanSummaryDto>, Serializable {
    private String planSetupId;
    private String planSetupName;
    private Integer deliveredFractions;
    private Integer plannedFractions;
    private Double deliveredDose;
    private Double plannedDose;
    private PlanStatusEnum status;
    private Date statusDate;
    private Date createdDt;
    private Date treatmentStartTime;
    private Date lastTreatmentTime;
    //    CourseId
    private Long courseId;
    //  Course
    private String course;
    //    Couse start time
    private Date courseStartTime;

    List<DoseSummaryDto> doseSummary;
    List<FieldSummaryDto> fields;

    private Boolean isISOCenterXYZValid = Boolean.TRUE;
    private String treatmentDevice;

    public void setDeliveredDose(Double deliveredDose){
        this.deliveredDose = NumberUtil.threeDecimalPlaceRoundOff(deliveredDose);
    }

    public void setPlannedDose(Double plannedDose){
        this.plannedDose = NumberUtil.threeDecimalPlaceRoundOff(plannedDose);
    }


    @Override
    public int compareTo(PlanSummaryDto planSummaryDto) {
        if(this.statusDate != null && planSummaryDto.getStatusDate() != null){
            return -this.statusDate.compareTo(planSummaryDto.statusDate);
        }
        int cmp = 0;
        if(this.courseId != null && planSummaryDto.getCourseId() != null) {
            cmp = this.courseId.compareTo(planSummaryDto.getCourseId());
        }
        if(cmp == 0){
            cmp = this.planSetupId.compareTo(planSummaryDto.getPlanSetupId());
        }
        return cmp;
    }
}
