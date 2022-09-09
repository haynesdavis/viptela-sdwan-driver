package com.ibm.sdwan.viptela.service;

import com.ibm.sdwan.viptela.model.viptela.KafkaMessage;

public interface InternalMessagingService {
    void sendVmanageApiAsyncResponse(KafkaMessage kafkaMessage);
}
