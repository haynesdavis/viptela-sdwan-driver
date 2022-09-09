package com.ibm.sdwan.viptela.model.viptela;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceData {

    private String deviceType;
    private String serialNumber;
    private String ncsDeviceName;
    private String configStatusMessage;
    ArrayList < Object > templateApplyLog = new ArrayList <>();
    private String uuid;
    private String managementSystemIP;
    private String templateStatus;
    private String chasisNumber;
    private String configStatusMessageDetails;
    private String configOperationMode;
    private String deviceModel;
    private String deviceState;
    private String validity;
    private String platformFamily;
    private String vedgeCertificateState;
    private String rootCertHash;
    private String deviceIP;
    private String personality;
    private String uploadSource;
    @JsonProperty("region-id")
    ArrayList < Object > regionId = new ArrayList < Object > ();
    @JsonProperty("domain-id")
    private String domainId;
    @JsonProperty("local-system-ip")
    private String localSystemIP;
    @JsonProperty("system-ip")
    private String systemIP;
    private String model_sku;
    @JsonProperty("site-id")
    private String siteId;
    @JsonProperty("host-name")
    private String hostName;
    @JsonProperty("sp-organization-name")
    private String spOrganizationName;
    private String version;
    private String vbond;
    @JsonProperty("vmanage-system-ip")
    private String vmanageSystemIP;
    private String vmanageConnectionState;
    private float lastupdated;
    private String reachability;
    @JsonProperty("uptime-date")
    private float uptimeDate;
    private String defaultVersion;
    @JsonProperty("organization-name")
    private String organizationName;
    ArrayList < Object > availableVersions = new ArrayList < Object > ();
    private String template;
    private String templateId;
    private boolean lifeCycleRequired;
    private String expirationDate;
    private String hardwareCertSerialNumber;
    private String subjectSerialNumber;
    private String resourceGroup;
    private String id;
    ArrayList < Object > tags = new ArrayList < Object > ();
    private String draftMode;

}
