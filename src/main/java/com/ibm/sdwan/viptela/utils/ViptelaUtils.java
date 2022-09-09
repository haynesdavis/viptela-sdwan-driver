package com.ibm.sdwan.viptela.utils;

import com.ibm.sdwan.viptela.config.SDWDriverProperties;
import com.ibm.sdwan.viptela.model.ExecutionRequest;

public class ViptelaUtils {

    public static long getDelay(SDWDriverProperties sdwDriverProperties, ExecutionRequest executionRequest) {
        long vManageApiRetryDelay = sdwDriverProperties.getvManageApiDelay().toMillis();
        Object vManageApiRetryDelayObject = executionRequest.getDeploymentLocation().getProperties()
                .get("vManageApiRetryDelay");
        if (vManageApiRetryDelayObject != null) {
            if (vManageApiRetryDelayObject instanceof String) {
                vManageApiRetryDelay = Long.parseLong((String) vManageApiRetryDelayObject);
            } else {
                vManageApiRetryDelay = (Long) vManageApiRetryDelayObject;
            }
            vManageApiRetryDelay = vManageApiRetryDelay * 1000;
        }
        return vManageApiRetryDelay;
    }
    
}
