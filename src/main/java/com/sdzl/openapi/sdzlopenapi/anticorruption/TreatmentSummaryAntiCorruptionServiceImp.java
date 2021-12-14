package com.sdzl.openapi.sdzlopenapi.anticorruption;

import com.sdzl.openapi.sdzlopenapi.model.treatmentsummary.PlanStatusEnum;
import com.sdzl.openapi.sdzlopenapi.model.treatmentsummary.PlanSummaryDto;
import com.sdzl.openapi.sdzlopenapi.model.treatmentsummary.TreatmentSummaryDto;
import com.varian.fhir.resources.TreatmentSummary;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTimeComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Treatment Summary Service Implementation.<br>
 */
@Slf4j
@Service
public class TreatmentSummaryAntiCorruptionServiceImp {
    @Autowired
    private FHIRTreatmentSummaryInterface fhirTreatmentSummaryInterface;

    /**
     * Default Constructor.<br>
     */
    public TreatmentSummaryAntiCorruptionServiceImp() {
        this.fhirTreatmentSummaryInterface = new FHIRTreatmentSummaryInterface();
    }

    /**
     * Return Approved Treatment Summary DTO.<br>
     *
     * @param id1 Patient Id
     * @return Treatment Summary DTO
     */
    public Optional<TreatmentSummaryDto> getApproveTxSummaryById1(String id1) {
        Optional<TreatmentSummaryDto> treatmentSummaryDtoOptional = this.getAllTxSummaryById1(id1);
        if(treatmentSummaryDtoOptional.isPresent()){
            treatmentSummaryDtoOptional.get().setLastTreatmentDate(null);
        }
        if(treatmentSummaryDtoOptional.isPresent() && treatmentSummaryDtoOptional.get().getPlans() != null){
//              需要将未审批的计划过滤掉
            Iterator<PlanSummaryDto> it = treatmentSummaryDtoOptional.get().getPlans().iterator();
            while(it.hasNext()){
                PlanSummaryDto dto = it.next();
                if(PlanStatusEnum.Unapproved.equals(dto.getStatus()) || PlanStatusEnum.Rejected.equals(dto.getStatus())
                        || PlanStatusEnum.Reviewed.equals(dto.getStatus())){
                    it.remove();
                }
            }
            if(treatmentSummaryDtoOptional.get().getPlans().isEmpty()){
                treatmentSummaryDtoOptional = Optional.empty();
            }
            if(treatmentSummaryDtoOptional.isPresent()) {
//        计算最后治疗日期
                Optional<Date> lastTreatmentDate = treatmentSummaryDtoOptional.get().getPlans().stream().filter(i -> i.getLastTreatmentTime() != null).map(i -> i.getLastTreatmentTime()).max(
                    DateTimeComparator.getInstance());
                if (lastTreatmentDate.isPresent()) {
                    treatmentSummaryDtoOptional.get().setLastTreatmentDate(lastTreatmentDate.get());
                }
            }
        }
        return treatmentSummaryDtoOptional;
    }

    /**
     * Return Treatment Summary DTO with no filter<br>
     *
     * @param id1 Patient Id
     * @return Treatment Summary DTO
     */
    public Optional<TreatmentSummaryDto> getAllTxSummaryById1(String id1) {
        TreatmentSummary treatmentSummary = null;
        try {
            treatmentSummary = fhirTreatmentSummaryInterface.getTreatmentSummaryById1(id1);
        } catch (Exception e) {
            log.error("getTreatmentSummary Exception:{}",e);
        }
        if (treatmentSummary == null || treatmentSummary.getCourses() == null || treatmentSummary.getCourses().size() == 0) {
            return Optional.empty();
        }
        TreatmentSummaryDto treatmentSummaryDto = TreatmentSummaryAssembler.getTreatmentSummaryDto(treatmentSummary);
        if(treatmentSummaryDto != null && treatmentSummaryDto.getPlans() != null){
            Collections.sort(treatmentSummaryDto.getPlans(), Comparator.comparing(PlanSummaryDto::getPlanSetupId));
        }
        return Optional.of(treatmentSummaryDto);
    }
}
