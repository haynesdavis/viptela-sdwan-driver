package com.ibm.sdwan.viptela.model.viptela;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceHeader {
    private String generatedOn;
    private Object viewKeys;
    private Object[] columns;
    private Object[] fields;
}
