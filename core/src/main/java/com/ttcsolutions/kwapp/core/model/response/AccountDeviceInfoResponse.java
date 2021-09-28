package com.ttcsolutions.kwapp.core.model.response;

import com.ttcsolutions.kwapp.core.model.AccountDeviceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

@AllArgsConstructor
@Data
@Accessors(chain = true)
public class AccountDeviceInfoResponse {
    private Long accountId;
    private String email;
    private String avatar;
    private Long deviceId;
    private String deviceCode;
    private String deviceName;
    private String relationship;
    private AccountDeviceStatus status;
    private String icon;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
