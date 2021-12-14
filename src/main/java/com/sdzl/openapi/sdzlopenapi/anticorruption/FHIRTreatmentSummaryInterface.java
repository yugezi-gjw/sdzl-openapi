package com.sdzl.openapi.sdzlopenapi.anticorruption;

import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.gclient.IQuery;
import ca.uhn.fhir.rest.gclient.StringClientParam;
import com.sdzl.openapi.sdzlopenapi.config.SysConfig;
import com.sdzl.openapi.sdzlopenapi.util.ApplicationContextUtil;
import com.varian.fhir.resources.TreatmentSummary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.instance.model.api.IBaseBundle;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FHIRTreatmentSummaryInterface extends FHIRInterface<TreatmentSummary> {

    @Autowired
    private SysConfig sysConfig;

    @Autowired
    private FHIRContextFactory factory;

    /**
     * Return Fhir TreatmentSummary by Patient Id.<br>
     *
     * @param patientId Patient Id
     * @return Fhir TreatmentSummary
     */
    public TreatmentSummary getTreatmentSummary(String patientId) throws Exception{
        TreatmentSummary treatmentSummary = null;
        IGenericClient client = factory.newRestfulGenericClient(sysConfig);
        List<TreatmentSummary> lstSummary;

        try {
            Map<String, String> parameters= new HashMap();
            parameters.put("patient", patientId);
            Bundle bundle = readBundleFromFhir(parameters, client, TreatmentSummary.class);
            if (bundle != null) {
                lstSummary = getListFromBundle(bundle);
                if (!lstSummary.isEmpty()) {
                    treatmentSummary = lstSummary.get(0);
                }
            }
        } catch (Exception e) {
            log.error("Exception: {}", e);
        }
        return treatmentSummary;
    }

    public TreatmentSummary getTreatmentSummaryById1(String id1) throws Exception{
        TreatmentSummary treatmentSummary = null;
        IGenericClient client = factory.newRestfulGenericClient(sysConfig);
        List<TreatmentSummary> lstSummary;

        try {
            Map<String, String> parameters= new HashMap();
            parameters.put(TreatmentSummary.SP_PATIENT_ID, id1);
            Bundle bundle = readBundleFromFhir(parameters, client, TreatmentSummary.class);
            if (bundle != null) {
                lstSummary = getListFromBundle(bundle);
                if (!lstSummary.isEmpty()) {
                    treatmentSummary = lstSummary.get(0);
                }
            }
        } catch (Exception e) {
            log.error("Exception: {}", e);
        }
        return treatmentSummary;
    }

    private <T extends IBaseResource> Bundle readBundleFromFhir(Map<String, String> parameters, IGenericClient client, Class<T> resourceType) {
        IQuery<IBaseBundle> query = client.search().forResource(resourceType);

        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            query = query.where(new StringClientParam(entry.getKey()).matchesExactly().value(entry.getValue()));
        }
        Bundle bundle = query.returnBundle(Bundle.class).execute();
        return bundle;
    }
}
