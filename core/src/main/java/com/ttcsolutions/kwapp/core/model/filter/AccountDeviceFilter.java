package com.ttcsolutions.kwapp.core.model.filter;

import com.ttcsolutions.kwapp.core.model.AccountDeviceStatus;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AccountDeviceFilter {
    private Long deviceId;
    private String deviceCode;
    private Long accountId;
    private AccountDeviceStatus status;
}
