package com.ibm.sdwan.viptela.model.viptela;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EdgeResponse {
    private float id;
    private String created;
    private float enterpriseId;
    private float siteId;
    private String activationKey;
    private String activationKeyExpires;
    private String activationState;
    private String activationTime;
    private String softwareVersion;
    private String buildNumber;
    private String factorySoftwareVersion;
    private String factoryBuildNumber;
    private String softwareUpdated;
    private String selfMacAddress;
    private String deviceId = null;
    private String logicalId;
    private String serialNumber = null;
    private String modelNumber;
    private String deviceFamily;
    private String name;
    private String dnsName = null;
    private String description = null;
    private float alertsEnabled;
    private float operatorAlertsEnabled;
    private String edgeState;
    private String edgeStateTime;
    private float isLive;
    private String systemUpSince;
    private String serviceUpSince;
    private String lastContact;
    private String serviceState;
    private String endpointPkiMode;
    private String haState;
    private String haPreviousState;
    private String haLastContact;
    private String haSerialNumber = null;
    private String bastionState;
    private String modified;
    private String customInfo = null;

}