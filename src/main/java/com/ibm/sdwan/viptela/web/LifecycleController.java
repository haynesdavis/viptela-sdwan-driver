package com.ibm.sdwan.viptela.web;

import com.ibm.sdwan.viptela.model.ExecutionAcceptedResponse;
import com.ibm.sdwan.viptela.model.ExecutionRequest;
import com.ibm.sdwan.viptela.service.LifecycleManagementService;
import com.ibm.sdwan.viptela.service.MessageConversionException;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.ibm.sdwan.viptela.utils.Constants.*;


@RestController("LifecycleController")
@RequestMapping("/api/driver")
public class LifecycleController {

    private final static Logger logger = LoggerFactory.getLogger(LifecycleController.class);

    private final LifecycleManagementService lifecycleManagementService;

    @Autowired
    public LifecycleController(final LifecycleManagementService lifecycleManagementService) {
        this.lifecycleManagementService = lifecycleManagementService;
    }

    @PostMapping("/lifecycle/execute")
    @ApiOperation(value = "Execute a lifecycle against a sdwan", notes = "Initiates a lifecycle ")
    public ResponseEntity<ExecutionAcceptedResponse> executeLifecycle(@RequestBody ExecutionRequest executionRequest, @RequestHeader(value = "tenantId", required = false) String tenantId) throws MessageConversionException{
        logger.info("Received request to execute a lifecycle [{}] ", executionRequest.getLifecycleName());
        logger.info("Received tenantId [{}] ",tenantId);
        tenantId = StringUtils.defaultIfEmpty(tenantId, "1");
        final ExecutionAcceptedResponse responseData = lifecycleManagementService.executeLifecycle(executionRequest, tenantId);
        if(tenantId.equals("1")){
            return ResponseEntity.accepted().body(responseData);
        }else{
            return ResponseEntity.accepted().headers(prepareHttpHeadersWithTenantId(tenantId)).body(responseData);
        }
    }

    private HttpHeaders prepareHttpHeadersWithTenantId(String tenantId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(TENANT_ID, tenantId);
        logger.info("httpserver {} ", httpHeaders.toString());
        return httpHeaders;
    }

}
