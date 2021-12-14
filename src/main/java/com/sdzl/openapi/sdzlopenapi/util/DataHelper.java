package com.sdzl.openapi.sdzlopenapi.util;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.hl7.fhir.dstu3.model.Reference;

/**
 * Created by fmk9441 on 2017-05-11.
 */
public class DataHelper {
    static List<String> separatorList = Arrays.asList("/","#","_","-");

    private DataHelper() {

    }

    /**
     * Return Reference String from Fhir Reference.<br>
     *
     * @param reference Fhir Reference
     * @return Reference String
     */
    public static String getReferenceType(Reference reference) {
        String value = StringUtils.EMPTY;
        if (reference != null && reference.hasReference()) {
//            List<String> separatorList = SystemConfigPool.queryReferenceSeparator();
            for(String separator : separatorList) {
                if (reference.getReference().indexOf(separator) > 0) {
                    value = reference.getReference().substring(0, reference.getReference().indexOf(separator));
                }
            }
            for(String separator : separatorList){
                if(value.indexOf(separator) !=-1) {
                    value = value.replace(separator, "");
                }
            }
        }
        return value;
    }

    /**
     * Return Reference Value String from Fhir Reference.<br>
     * @param reference Fhir Reference
     * @return Reference Value String
     */
    public static String getReferenceValue(Reference reference) {
        String value = StringUtils.EMPTY;
        if (null != reference && reference.hasReference()) {
            value = getReferenceValue(reference.getReference());
        }
        return value;
    }


    /**
     * Return Reference Value String by value String.<br>
     * @param value Reference Value
     * @return result
     */
    public static String getReferenceValue(String value) {
        if (isNotBlank(value)) {
            value = getString(value);
        }
        return value;
    }

    /**
     * Return Fhir Reference by Id, Display, Resource Name, and contained.<br>
     * @param id Id
     * @param display Display
     * @param resourceName Resource Name
     * @param contained Contained
     * @return Fhir Reference
     */
    public static Reference getReference(String id, String display, String resourceName, boolean contained) {
        String t = contained ? (StringUtils.startsWith(id, "#") ? id : ("#" + resourceName + "/" + id)) : resourceName + "/" + id;
        Reference reference = new Reference().setReference(t).setDisplay(display);
        reference.setId(t);
        return reference;
    }


    private static String getString(String value) {
        if (isNotBlank(value)) {
            for(String separator : separatorList) {
                if (value.indexOf(separator) > 0) {
                    value = value.substring(value.indexOf(separator)+1);
                }
            }
            for(String separator : separatorList) {
                if (value.indexOf(separator) != -1) {
                    value = value.replace(separator,"");
                }
            }
        }
        return value;
    }
}