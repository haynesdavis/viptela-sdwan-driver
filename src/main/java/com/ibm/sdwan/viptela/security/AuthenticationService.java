package com.ibm.sdwan.viptela.security;

import com.ibm.sdwan.viptela.driver.SdwanResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.SocketException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static com.ibm.sdwan.viptela.config.SDWDriverConstants.RC_SERVER_URL;
import static com.ibm.sdwan.viptela.utils.Constants.V_MANAGE_PASSWORD;
import static com.ibm.sdwan.viptela.utils.Constants.V_MANAGE_USER;

@Service
public class AuthenticationService {
    private final static Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    private final RestTemplate restTemplate;
    @Autowired
    public AuthenticationService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }
    private String getJsessionIdFromResponeHeader ( HttpHeaders headers ){
        String sessionId="";
        String setCookie = headers.getFirst(HttpHeaders.SET_COOKIE);
        if (!StringUtils.hasLength(setCookie)) {
            logger.error("Could not find Cookie in the response header");
          throw new SdwanResponseException("Cookie not found in the response header");
        }
        String pattern1 = "JSESSIONID=";
        String pattern2 = ";";
        Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
        Matcher m = p.matcher(setCookie);
        if(m.find()) {
            sessionId = m.group(1);
        }
        return sessionId;
    }

    public void logout(Map <String, String> authenticationProperties, String jsessionId) {
        String logOutUrl = authenticationProperties.get(RC_SERVER_URL) + "/logout?nocache";
        HttpHeaders httpHeaders =new HttpHeaders();
        httpHeaders.set("Cookie", "JSESSIONID="+jsessionId);
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(logOutUrl, HttpMethod.GET, httpEntity, String.class);
        if(response.getStatusCode()== HttpStatus.OK) {
            logger.info("Logout successful!");
        }else{
            logger.info("Logout failed!");
        }
    }

    public String getJsessionId(Map <String, String> authenticationProperties) throws ResourceAccessException {
        HttpHeaders httpHeadersForJsessionId = new HttpHeaders();
        httpHeadersForJsessionId.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String userName = authenticationProperties.get(V_MANAGE_USER);
        String password = authenticationProperties.get(V_MANAGE_PASSWORD);
        String payloadJSessionId = "j_username="+userName+"&j_password="+password;
        final HttpEntity<String> requestEntityJSessionId = new HttpEntity<>(payloadJSessionId, httpHeadersForJsessionId);
        String urlJSessionId = authenticationProperties.get(RC_SERVER_URL) + "/j_security_check";
        logger.info("URL for getting JSESSIONID :" + urlJSessionId); 
        final ResponseEntity<String> responseEntityJSessionId = restTemplate.postForEntity(urlJSessionId, requestEntityJSessionId, String.class);   
        if(responseEntityJSessionId.getBody()!=null) {
             logger.info("Authentication failed");
             throw new AuthenticationException("Authentication failed");
         }
        logger.info("Authentication successful");
        return getJsessionIdFromResponeHeader(responseEntityJSessionId.getHeaders());
    }
    // //Need to be removed this function, added for simulating SocketErrorException
    // public String getJsessionId_error1(Map <String, String> authenticationProperties, int retryCount) throws ResourceAccessException {
    //     HttpHeaders httpHeadersForJsessionId = new HttpHeaders();
    //     httpHeadersForJsessionId.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    //     String userName = authenticationProperties.get(V_MANAGE_USER);
    //     String password = authenticationProperties.get(V_MANAGE_PASSWORD);
    //     String payloadJSessionId = "j_username="+userName+"&j_password="+password;
    //     final HttpEntity<String> requestEntityJSessionId = new HttpEntity<>(payloadJSessionId, httpHeadersForJsessionId);
    //     String urlJSessionId = authenticationProperties.get(RC_SERVER_URL) + "/j_security_check";
    //     logger.info("URL for getting JSESSIONID :" + urlJSessionId); 
    //     final ResponseEntity<String> responseEntityJSessionId = restTemplate.postForEntity(urlJSessionId, requestEntityJSessionId, String.class);   
    //     if(responseEntityJSessionId.getBody()!=null) {
    //          logger.info("Authentication failed");
    //          throw new AuthenticationException("Authentication failed");
    //      }
    //      String jsessionId = getJsessionIdFromResponeHeader(responseEntityJSessionId.getHeaders());
    //     if(retryCount >= 10){
    //         logger.info("Explicitly throwing  SocketException-ResourceAccessException");
    //         try {
    //             logout(authenticationProperties, jsessionId);
    //             throw new SocketException("socket timeout");
    //         } catch (SocketException e) {
    //             throw new ResourceAccessException("socket timeout");
    //         }       
    //     }
    //     else{
    //         logger.info("Authentication successful");
    //         return getJsessionIdFromResponeHeader(responseEntityJSessionId.getHeaders());
    //     }
    // }
    // public String getJsessionId_error2(Map <String, String> authenticationProperties, int retryCount) throws ResourceAccessException {
    //     HttpHeaders httpHeadersForJsessionId = new HttpHeaders();
    //     httpHeadersForJsessionId.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    //     String userName = authenticationProperties.get(V_MANAGE_USER);
    //     String password = authenticationProperties.get(V_MANAGE_PASSWORD);
    //     String payloadJSessionId = "j_username="+userName+"&j_password="+password;
    //     final HttpEntity<String> requestEntityJSessionId = new HttpEntity<>(payloadJSessionId, httpHeadersForJsessionId);
    //     String urlJSessionId = authenticationProperties.get(RC_SERVER_URL) + "/j_security_check";
    //     logger.info("URL for getting JSESSIONID :" + urlJSessionId); 
    //     final ResponseEntity<String> responseEntityJSessionId = restTemplate.postForEntity(urlJSessionId, requestEntityJSessionId, String.class);   
    //     if(responseEntityJSessionId.getBody()!=null) {
    //          logger.info("Authentication failed");
    //          throw new AuthenticationException("Authentication failed");
    //      }
    //      String jsessionId = getJsessionIdFromResponeHeader(responseEntityJSessionId.getHeaders());
    //     if(retryCount >= 0){
    //         logger.info("Explicitly throwing  SocketException-ResourceAccessException");
    //         try {
    //             logout(authenticationProperties, jsessionId);
    //             throw new SocketException("socket timeout");
    //         } catch (SocketException e) {
    //             throw new ResourceAccessException("socket timeout");
    //         }       
    //     }
    //     else{
    //         logger.info("Authentication successful");
    //         return getJsessionIdFromResponeHeader(responseEntityJSessionId.getHeaders());
    //     }
    // }
    public String getXsrfToken(Map<String, String> authenticationProperties, String jsessionId) {
        HttpHeaders httpHeadersForXSRFToken = new HttpHeaders();
        httpHeadersForXSRFToken.setContentType(MediaType.APPLICATION_JSON);
        httpHeadersForXSRFToken.set("Cookie", "JSESSIONID=" + jsessionId);
        final HttpEntity<String> requestEntityXSRFToken = new HttpEntity<>(httpHeadersForXSRFToken);
        String urlXSRFToken = authenticationProperties.get(RC_SERVER_URL) + "/dataservice/client/token";
        ResponseEntity<String> responseEntityXSRFToken = restTemplate.exchange(urlXSRFToken, HttpMethod.GET,
                requestEntityXSRFToken, String.class);
        return responseEntityXSRFToken.getBody();
    }  
}
