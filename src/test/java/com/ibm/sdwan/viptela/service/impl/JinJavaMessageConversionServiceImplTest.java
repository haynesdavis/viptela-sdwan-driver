 package com.ibm.sdwan.viptela.service.impl;

 import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.ibm.sdwan.viptela.model.ExecutionRequest;
import com.ibm.sdwan.viptela.model.ExecutionRequestPropertyValue;
import com.ibm.sdwan.viptela.model.GenericExecutionRequestPropertyValue;
import com.ibm.sdwan.viptela.service.MessageConversionException;
import com.ibm.sdwan.viptela.service.MissingPropertyException;
import com.ibm.sdwan.viptela.service.TemplateException;

 @SpringBootTest
 public class JinJavaMessageConversionServiceImplTest {

 	private static final String TEMPLATE_PATH = "templates/";

 	@Test
 	@DisplayName("Testing positive scenario for Generate Message From Request")
 	public void testGenerateMessageFromRequestForCreate() throws MessageConversionException, IOException {
 		JinJavaMessageConversionServiceImpl jinJavaMessageConversionService = new JinJavaMessageConversionServiceImpl();
 		ExecutionRequest executionRequest = new ExecutionRequest();
 		executionRequest.setLifecycleName("Create");
 		Map<String, ExecutionRequestPropertyValue> resourceProperties = new HashMap<>();
 		resourceProperties.put("templateId", new GenericExecutionRequestPropertyValue("templateId"));
 		resourceProperties.put("deviceUuid", new GenericExecutionRequestPropertyValue("deviceUuid"));
 		resourceProperties.put("lanGateway", new GenericExecutionRequestPropertyValue("lanGateway"));
 		resourceProperties.put("lanIP", new GenericExecutionRequestPropertyValue("lanIP"));
 		resourceProperties.put("wanGateway", new GenericExecutionRequestPropertyValue("wanGateway"));
 		resourceProperties.put("hostname", new GenericExecutionRequestPropertyValue("hostname"));
 		resourceProperties.put("systemIP", new GenericExecutionRequestPropertyValue("systemIP"));
 		resourceProperties.put("siteId", new GenericExecutionRequestPropertyValue("siteId"));

 		executionRequest.setResourceProperties(resourceProperties);

 		String parsedTemplate = jinJavaMessageConversionService.generateMessageFromRequest("AttachDevice",
 				executionRequest);

 		String expectedTemplate = "";
 		try (InputStream inputStream = JinJavaMessageConversionServiceImplTest.class
 				.getResourceAsStream("/" + TEMPLATE_PATH + "/ExpectedAttachDevice.json")) {
 			if (inputStream != null) {
 				expectedTemplate = IOUtils.toString(inputStream, Charset.defaultCharset());
 			}
 		} catch (IOException e) {
 			throw e;
 		}
 		assertEquals(expectedTemplate.trim(), parsedTemplate.trim());
 	}
 	
	@Test
 	@DisplayName("Testing positive scenario for Generate Message From Request - throw TemplateException")
 	public void testGenerateMessageFromRequestForCreateTemplateException() throws MessageConversionException, IOException {
 		JinJavaMessageConversionServiceImpl jinJavaMessageConversionService = new JinJavaMessageConversionServiceImpl();
 		ExecutionRequest executionRequest = new ExecutionRequest();
 		executionRequest.setLifecycleName("Create");
 		Map<String, ExecutionRequestPropertyValue> resourceProperties = new HashMap<>();
 		resourceProperties.put("templateId", new GenericExecutionRequestPropertyValue("templateId"));
 		resourceProperties.put("deviceUuid", new GenericExecutionRequestPropertyValue("deviceUuid"));
 		resourceProperties.put("lanGateway", new GenericExecutionRequestPropertyValue("lanGateway"));
 		resourceProperties.put("lanIP", new GenericExecutionRequestPropertyValue("127.0.0.1"));
 		resourceProperties.put("wanGateway", new GenericExecutionRequestPropertyValue("wanGateway"));
 		resourceProperties.put("hostname", new GenericExecutionRequestPropertyValue("hostname"));
 		resourceProperties.put("systemIP", new GenericExecutionRequestPropertyValue("systemIP"));
 		resourceProperties.put("siteId", new GenericExecutionRequestPropertyValue("001"));

 		executionRequest.setResourceProperties(resourceProperties);
 		assertThrows(TemplateException.class, () -> {
 			jinJavaMessageConversionService.generateMessageFromRequest(null,
 	 				executionRequest);
 		});
 	}
	
	@Test
 	@DisplayName("Testing positive scenario for Generate Message From Request - templateId value is null")
 	public void testGenerateMessageFromRequestForCreateTemplateIdIsNull() throws MessageConversionException, IOException {
 		JinJavaMessageConversionServiceImpl jinJavaMessageConversionService = new JinJavaMessageConversionServiceImpl();
 		ExecutionRequest executionRequest = new ExecutionRequest();
 		executionRequest.setLifecycleName("Create");
 		Map<String, ExecutionRequestPropertyValue> resourceProperties = new HashMap<>();
 		resourceProperties.put("deviceUuid", new GenericExecutionRequestPropertyValue("deviceUuid"));
 		resourceProperties.put("lanGateway", new GenericExecutionRequestPropertyValue("lanGateway"));
 		resourceProperties.put("lanIP", new GenericExecutionRequestPropertyValue("127.0.0.1"));
 		resourceProperties.put("wanGateway", new GenericExecutionRequestPropertyValue("wanGateway"));
 		resourceProperties.put("hostname", new GenericExecutionRequestPropertyValue("hostname"));
 		resourceProperties.put("systemIP", new GenericExecutionRequestPropertyValue("systemIP"));
 		resourceProperties.put("siteId", new GenericExecutionRequestPropertyValue("001"));

 		executionRequest.setResourceProperties(resourceProperties);
 		assertThrows(MissingPropertyException.class, () -> {
 			jinJavaMessageConversionService.generateMessageFromRequest("AttachDevice",
 	 				executionRequest);
 		});
 	}

 	@Test
 	@DisplayName("Testing positive scenario to Create for Extract Properties From Message")
 	public void testExtractPropertiesFromMessageForCreate() {
 		JinJavaMessageConversionServiceImpl jinJavaMessageConversionService = new JinJavaMessageConversionServiceImpl();
 		ExecutionRequest executionRequest = new ExecutionRequest();
 		Map<String, Object> expectedOutputs = new HashMap<>();
 		expectedOutputs.put("id", 0);
 		expectedOutputs.put("activationKey", "myActivationKey");
 		Map<String, Object> internalMap = new HashMap<>();
 		internalMap.put("certificate", "myCertficate");
 		internalMap.put("ca-certificate", "myCertficate");
 		internalMap.put("privateKey", "myPrivateKey");
 		internalMap.put("privateKeyPassword", "myPrivateKeyPassword");
 		internalMap.put("csr", "myCsr");
 		expectedOutputs.put("generatedCertificate", internalMap);
 		String jsonMessage = "{\n" + "  \"id\": 0,\n" + "  \"activationKey\": \"myActivationKey\",\n"
 				+ "  \"generatedCertificate\": {\n" + "    \"certificate\": \"myCertficate\",\n"
 				+ "    \"ca-certificate\": \"myCaCertificate\",\n" + "    \"privateKey\": \"myPrivateKey\",\n"
 				+ "    \"privateKeyPassword\": \"myPrivateKeyPassword\",\n" + "    \"csr\": \"myCsr\"\n" + "  }\n"
 				+ "}";
 		Map<String, Object> receivedOutputs = jinJavaMessageConversionService.extractPropertiesFromMessage(jsonMessage);
 		assertEquals(expectedOutputs.get("id"), receivedOutputs.get("id"));
 		assertEquals(expectedOutputs.get("activationKey"), receivedOutputs.get("activationKey"));
 	}
 	
 }
