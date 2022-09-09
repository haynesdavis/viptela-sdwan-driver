package com.ibm.sdwan.viptela.service;

import com.ibm.sdwan.viptela.model.ExecutionRequest;

import java.util.Map;

public interface MessageConversionService {

    String generateMessageFromRequest(String messageType, ExecutionRequest executionRequest) throws MessageConversionException;
    Map<String, Object> extractPropertiesFromMessage(String message);

}