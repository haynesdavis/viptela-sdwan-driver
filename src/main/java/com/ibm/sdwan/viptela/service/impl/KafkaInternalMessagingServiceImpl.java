package com.ibm.sdwan.viptela.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.sdwan.viptela.config.SDWDriverProperties;
import com.ibm.sdwan.viptela.driver.SdwanResponseException;
import com.ibm.sdwan.viptela.model.viptela.KafkaMessage;
import com.ibm.sdwan.viptela.service.InternalMessagingService;

@Service
public class KafkaInternalMessagingServiceImpl implements InternalMessagingService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaInternalMessagingServiceImpl.class);

    private final SDWDriverProperties properties;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public KafkaInternalMessagingServiceImpl(SDWDriverProperties properties,
            KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.properties = properties;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void sendVmanageApiAsyncResponse(KafkaMessage kafkaMessage) {
        String message;
        try {
            message = objectMapper.writeValueAsString(kafkaMessage);
            ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(properties.getTopics().getVManageApiRetryTopic(), message);
            future.addCallback(sendResult -> logger.debug("KafkaMessage successfully sent"),
                               exception  -> logger.error("Exception while sending KafkaMessage", exception));
        } catch (JsonProcessingException e) {
            logger.error("Exception while generating message text from KafkaMessage", e.getMessage());
            throw new SdwanResponseException("Error while posting message to Kafka topic");
        }

    }
}
