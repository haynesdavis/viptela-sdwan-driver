package com.ibm.sdwan.viptela.model.viptela;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EdgeNotification {
    private String alertTime;
    private String alertType;
    private String customer;
    private String customerLogicalId;
    private String deviceLogicalId;
    private String entityAffected;
    private String lastContact;
    private String message;
    private String vco;
}
