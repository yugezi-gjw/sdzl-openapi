package com.sdzl.openapi.sdzlopenapi.anticorruption;

import ca.uhn.fhir.rest.gclient.ICriterion;
import ca.uhn.fhir.rest.gclient.IQuery;
import ca.uhn.fhir.rest.server.exceptions.BaseServerResponseException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.DomainResource;
import org.hl7.fhir.instance.model.api.IBaseBundle;
import org.hl7.fhir.instance.model.api.IBaseResource;

/**
 * Created by bhp9696 on 2017/11/15.
 */
@Slf4j
public class FHIRInterface<T extends DomainResource> {


    protected IQuery<IBaseBundle> buildIQuery(IQuery<IBaseBundle> iQuery, ICriterion<?> iCriterion, boolean first) {
        IQuery<IBaseBundle> tmpIQuery;
        if (first) {
            tmpIQuery = iQuery.where(iCriterion);
        } else {
            tmpIQuery = iQuery.and(iCriterion);
        }
        return tmpIQuery;
    }


    protected List<T> getListFromBundle(Bundle bundle) {
        List<T> lstPatient = new ArrayList<>();
        if (bundle.hasEntry()) {
            bundle.getEntry().forEach(bundleEntryComponent -> {
                if (bundleEntryComponent.hasResource()) {
                    T resource = (T) bundleEntryComponent.getResource();
                    lstPatient.add(resource);
                }
            });
        }
        return lstPatient;
    }

    protected List<T> getListFromBundleEntryComponent(List<Bundle.BundleEntryComponent> bundleEntryComponentList){
        List<T> resultList = new ArrayList<>();
        bundleEntryComponentList.forEach(bundleEntryComponent -> {
            if (bundleEntryComponent.hasResource()) {
                T resource = (T) bundleEntryComponent.getResource();
                resultList.add(resource);
            }
        });
        return resultList;
    }

    protected Bundle queryPagingBundle(IQuery<IBaseBundle> iQuery, List<Class<? extends IBaseResource>> theTypes, int countPerPage) {
        if( theTypes != null){
            iQuery = iQuery.preferResponseTypes(theTypes);
        }
        return iQuery.count(countPerPage)
                .returnBundle(Bundle.class)
                .execute();
    }
    
    protected void logFhirException(Exception e) {
        if (e instanceof BaseServerResponseException) {
            BaseServerResponseException fhirServerException = (BaseServerResponseException) e;
            String body = fhirServerException.getResponseBody();
            log.info("FhirException:{}", body);
            log.debug("FhirException cause:{}", fhirServerException.getCause());
        }
    }

}
