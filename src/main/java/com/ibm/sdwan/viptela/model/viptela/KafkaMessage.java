package com.ibm.sdwan.viptela.model.viptela;

import com.ibm.sdwan.viptela.model.ExecutionRequest;

import lombok.Data;

@Data
public class KafkaMessage {

    private String requestId;
    private String status;      
    private ExecutionRequest executionRequest;
    private int vManageApiRetryCount;
    private String tenantId;
    
   
}


