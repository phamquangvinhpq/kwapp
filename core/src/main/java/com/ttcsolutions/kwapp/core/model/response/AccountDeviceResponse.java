package com.ttcsolutions.kwapp.core.model.response;

import java.time.OffsetDateTime;

import com.ttcsolutions.kwapp.core.model.AccountDeviceStatus;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * The object which is transfer to response body.
 * 
 * @author mahai
 *
 */
@Data
@Accessors(chain = true)
public class AccountDeviceResponse {
	private Long accountId;
	private Long deviceId;
	private String deviceCode;
	private String deviceName;
	private String relationship;
	private AccountDeviceStatus status;
	private String icon;
	private OffsetDateTime createdAt;
	private OffsetDateTime updatedAt;
}
