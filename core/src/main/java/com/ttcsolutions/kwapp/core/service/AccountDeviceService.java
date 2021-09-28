package com.ttcsolutions.kwapp.core.service;

import com.ttcsolutions.kwapp.commons.model.KwAccount;
import com.ttcsolutions.kwapp.core.model.filter.AccountDeviceFilter;
import com.ttcsolutions.kwapp.core.model.request.CreateAccountDeviceRequest;
import com.ttcsolutions.kwapp.core.model.response.AccountDeviceInfoResponse;
import com.ttcsolutions.kwapp.core.model.response.AccountDeviceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Business process for Device device management.
 * @author mahai
 *
 */
public interface AccountDeviceService {
	/**
	 * The function connect the logged in account with a device by deviceId
	 * @return
	 */
	AccountDeviceResponse create(KwAccount kwAccount, CreateAccountDeviceRequest createAccountDeviceRequest);

	Page<AccountDeviceInfoResponse> getAccountDeviceInfos(AccountDeviceFilter filter, Pageable pageable);
}
