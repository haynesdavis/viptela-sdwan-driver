package com.ibm.sdwan.viptela.service;

import com.ibm.sdwan.viptela.model.alm.ExecutionAsyncResponse;

import java.time.Duration;


public interface ExternalMessagingService {

    void sendExecutionAsyncResponse(ExecutionAsyncResponse request, String tenantId);

    void sendDelayedExecutionAsyncResponse(ExecutionAsyncResponse request, String tenantId, Duration delay);


}