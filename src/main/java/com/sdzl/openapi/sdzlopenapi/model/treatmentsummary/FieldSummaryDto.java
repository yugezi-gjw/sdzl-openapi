package com.sdzl.openapi.sdzlopenapi.model.treatmentsummary;

import lombok.Data;

@Data
public class FieldSummaryDto {
    private boolean setupField; //是否是Setup（摆位野）
    String fieldId;  //野Id
    String fieldName;  //野名称
    String machineId; //设备ID
    String machine; //设备
    String energy;//能量
    String scale;//标准
    String mu;//跳数
    String gantryRotation;//机架转角
    Double collimatorRotation;//准直器转角
    Double collimatorX;
    Double collimatorY;
    String collimatorMode;
    Double couchRotation;//床转角
    Double couchLat;
    Double couchLng;
    Double couchVrt;
    Double couchLatDelta;
    Double couchLngDelta;
    Double couchVrtDelta;
    String size;//照射尺寸
    String wedge;//楔形板
    Double weight;//权重
    String technique;
    Integer doseRate;
    String toleranceId;
    String toleranceName;
    String isoCenterX;//X
    String isoCenterY;//Y
    String isoCenterZ;//Z
    String calculatedSSD;//源皮距

    String rotationUnit;
    String collUnit;
    String couchUnit;
    String isoCenterUnit;
    String doseRateUnit;
    String ssdUnit;
}
