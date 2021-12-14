package com.sdzl.openapi.sdzlopenapi.model.patient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by asharma0 on 12/21/2016.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {
    /**
     * Patient Id in ARIA
     */
    private String id1;
    private String id2;
    private String id3;
    private String id4;
    private String id5;
    private String id6;
    private String id7;
    private String ariaId;
    private String hisId;
    private String nationalId;
    private String chineseName;
    private String englishName;
    private String pinyin;
    private String gender;
    private Date birthday;
    private String contactPerson;
    private String contactPhone;
    private String contactPerson2;
    private String contactPhone2;
    private String patientSer;
    private String physicianGroupId;
    private String physicianId;
    private String physicianName;
    private String physicianBId;
    private String physicianBName;
    private String physicianCId;
    private String physicianCName;
    //医生电话
    private String physicianPhone;
    //医生二电话OCCT-1179
    private String physicianBPhone;
    //医生三电话OCCT-1179
    private String physicianCPhone;
    private String telephone;
    private String address;
    private List<PatientLabel> labels;
    private Date createdDT;
    private byte[] photo;
    private String cpTemplateId;
    private String cpTemplateName;
    // FIXME Task 313510:Server: anti- 保存新的输入项
    /** ECOG评分: 0,1,2,3,4 or free text,32 English chars */
    private String ecogScore;
    /** ECOG评分描述 */
    private String ecogDesc;
    /** 阳性标识: free text, 32 English chars */
    private String positiveSign;
    /** 医疗保险类型 */
    private String insuranceType;
    /** 患者来源: 门诊/住院 */
    private String patientSource;

    private boolean urgent;

    /** OCCT-2527 **/
    private Boolean inPatientReserve;
    private Boolean clinicalTrial;
    /** end **/

    private String patientHistory;

    /**
     * 前端患者列表的展示场景，内容可参考PatientViewScenario中定义
     */
    private int patientViewScenario;

    private boolean disableNextActionBtn;

    public void addPatientLabel(PatientLabel patientLabel) {
        if (labels == null) {
            labels = new ArrayList<>();
        }
        labels.add(patientLabel);
    }

    @Data
    public static class PatientLabel {
        private String labelId;
        private String labelTag;
        private String labelText;
    }
}