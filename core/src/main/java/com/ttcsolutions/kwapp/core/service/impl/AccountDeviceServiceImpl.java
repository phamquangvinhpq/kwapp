package com.ttcsolutions.kwapp.core.service.impl;

import com.ttcsolutions.kwapp.commons.exception.BusinessException;
import com.ttcsolutions.kwapp.commons.model.KwAccount;
import com.ttcsolutions.kwapp.core.model.AccountDeviceStatus;
import com.ttcsolutions.kwapp.core.model.DeviceStatus;
import com.ttcsolutions.kwapp.commons.util.ErrorCode;
import com.ttcsolutions.kwapp.core.mapper.CreateAccountDevice2EntityMapper;
import com.ttcsolutions.kwapp.core.mapper.Entity2AccountDeviceMapper;
import com.ttcsolutions.kwapp.core.model.filter.AccountDeviceFilter;
import com.ttcsolutions.kwapp.core.model.request.CreateAccountDeviceRequest;
import com.ttcsolutions.kwapp.core.model.response.AccountDeviceInfoResponse;
import com.ttcsolutions.kwapp.core.model.response.AccountDeviceResponse;
import com.ttcsolutions.kwapp.core.repository.AccountDeviceRepository;
import com.ttcsolutions.kwapp.core.service.AccountDeviceService;
import com.ttcsolutions.kwapp.core.service.DeviceInternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class AccountDeviceServiceImpl implements AccountDeviceService {
    private final AccountDeviceRepository accountDeviceRepository;
    private final DeviceInternalService deviceInternalService;

    @Override
    @Transactional
    public AccountDeviceResponse create(KwAccount kwAccount, CreateAccountDeviceRequest createAccountDeviceRequest) {
        var deviceCode = createAccountDeviceRequest.getDeviceCode();
        var device = deviceInternalService.getDevice0(deviceCode);
        if (DeviceStatus.INACTIVATED == device.getStatus()) {
            throw new BusinessException(ErrorCode.DEVICE_INACTIVE,
                    "Device with code: " + createAccountDeviceRequest.getDeviceCode() + " is inactive.");
        }
        var admin = true;
        var status = AccountDeviceStatus.ACTIVE;
        if (accountDeviceRepository.existsByDeviceCode(deviceCode)) {
            admin = false;
            status = AccountDeviceStatus.PENDING;
            if (accountDeviceRepository.existsByDeviceCodeAndAccountId(deviceCode, kwAccount.getId())) {
                throw new BusinessException(ErrorCode.DEVICE_ALREADY_CONNECTED,
                        "Device with code: " + createAccountDeviceRequest.getDeviceCode() + " is already connected.");
            }
        }
        var now = OffsetDateTime.now();
        var accountDevice = CreateAccountDevice2EntityMapper.INSTANCE.map(createAccountDeviceRequest)
                .setCreatedAt(now)
                .setUpdatedAt(now)
                .setAdmin(admin)
                .setStatus(status)
                .setAccountId(kwAccount.getId())
                .setDeviceId(device.getId())
                .setDeviceCode(deviceCode);
        return Entity2AccountDeviceMapper.INSTANCE.map(accountDeviceRepository.insert(accountDevice));
    }

    @Override
    public Page<AccountDeviceInfoResponse> getAccountDeviceInfos(AccountDeviceFilter filter, Pageable pageable) {
        return accountDeviceRepository.getAccountDeviceInfos(filter, pageable);
    }
}
