package com.ibm.sdwan.viptela.service.impl;

import javax.annotation.PreDestroy;

import com.ibm.sdwan.viptela.driver.SdwanResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.sdwan.viptela.config.SDWDriverProperties;
import com.ibm.sdwan.viptela.model.ExecutionRequest;
import com.ibm.sdwan.viptela.model.viptela.KafkaMessage;
import com.ibm.sdwan.viptela.service.LifecycleManagementService;
import com.ibm.sdwan.viptela.service.MessageConversionException;
import com.ibm.sdwan.viptela.utils.ViptelaUtils;

@Component
public class VManageApiRetryServiceListener {
    private static final Logger logger = LoggerFactory.getLogger(VManageApiRetryServiceListener.class);


    private final SDWDriverProperties sdwDriverProperties;
    private final ObjectMapper objectMapper;
    private final LifecycleManagementService lifecycleManagementService;

    @Autowired
    public VManageApiRetryServiceListener(
            SDWDriverProperties sdwDriverProperties, ObjectMapper objectMapper,
            LifecycleManagementService lifecycleManagementService) {
        this.sdwDriverProperties = sdwDriverProperties;
        this.objectMapper = objectMapper;
        this.lifecycleManagementService = lifecycleManagementService;
    }

    @PreDestroy
    public void close() {
        logger.info("Shutting down Delete Lifecycle Management Operation for EdgeStatus ...");
    }

    @KafkaListener(topics = "${rcdriver.topics.vManageApiRetryTopic}")
    public void listenForVManageApiRetryMessages(final String message) throws MessageConversionException {
        final KafkaMessage kafkaMessage;
        try {
            // Deserialize message into KafkaMessage
            kafkaMessage = objectMapper.readValue(message, KafkaMessage.class);
        } catch (JsonProcessingException e) {
            logger.error("Exception while reading ExecutionRequest from kafka message", e);
            throw new SdwanResponseException("Exception while reading ExecutionRequest from Kafka message");
        }
        String status = kafkaMessage.getStatus();
        String requestId = kafkaMessage.getRequestId();
        ExecutionRequest executionRequest = kafkaMessage.getExecutionRequest();
        int vManageApiRetryCount = kafkaMessage.getVManageApiRetryCount();
        String tenantId = kafkaMessage.getTenantId();
        logger.debug("Status received from kafkaMessage : " +status);
        switch (status) {
            case "FAILED":
                logger.debug("Inside FAILED status execution ");
                long vManageApiRetryDelay = ViptelaUtils.getDelay(sdwDriverProperties, executionRequest);
                // wait here
                try {
                    Thread.sleep(vManageApiRetryDelay);
                } catch (InterruptedException e) {
                    logger.error("Thread interrupted during sleep", e);
                    throw new SdwanResponseException("Internal error while thread sleep");
                }
                // invoke lifecycle again
                logger.debug("Exceuting runlifecycle again from listener for " +vManageApiRetryCount +" time");
                lifecycleManagementService.runLifecycle(requestId, executionRequest, --vManageApiRetryCount, tenantId);
                break;
            default:
                logger.error("Internal error, kafka message status "+status+" is not supported ");
                throw new SdwanResponseException(String.format(
                        "Internal error, kafka message status [%s] is not supported", status));
        }

    }
}