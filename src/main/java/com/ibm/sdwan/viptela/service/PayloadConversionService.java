package com.ibm.sdwan.viptela.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ibm.sdwan.viptela.driver.SdwanResponseException;
import com.ibm.sdwan.viptela.model.viptela.DeviceDetails;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
import static com.ibm.sdwan.viptela.utils.Constants.*;

@Service
@Slf4j
public class PayloadConversionService {
    private final static Logger logger = LoggerFactory.getLogger(PayloadConversionService.class);
    ObjectMapper mapper = new ObjectMapper();
    public String buildPayloadForSyncSmart(Map<String, Object> properties) {
        String syncSmartUser = (String)properties.get(SMART_ACCOUNT_USER);
        String syncSmartPassword = (String)properties.get(SMART_ACCOUNT_PASSWORD);
        if(!StringUtils.hasLength(syncSmartUser)){
            throw new MissingPropertyException("Deployment Location property "+ SMART_ACCOUNT_USER + " must be passed.");
        }
        if(syncSmartPassword == null){
            throw new MissingPropertyException("Deployment Location property "+ SMART_ACCOUNT_PASSWORD + " must be passed.");
        }
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put("username", syncSmartUser);
        rootNode.put("password", syncSmartPassword);
        rootNode.put("validity_string", VALID_STRING); 
        final String jsonString;
        try {
            jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
        } catch (JsonProcessingException e) {
            logger.error("Could not build payload for sync smart account API");
            throw new SdwanResponseException("payload creation for sync smart account failed! ");
        }
        return jsonString;
    }

    public DeviceDetails extractDevicesFromResponse(String response) {
        try {
            return mapper.readValue(response, DeviceDetails.class);
        } catch (JsonProcessingException e) {
            logger.error("Could not read DeviceDetails Java object from Json String");
            throw  new SdwanResponseException("Could not read DeviceDetails Java object from Json");
        }
    }
}
