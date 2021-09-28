package com.ttcsolutions.kwapp.core.service.impl;

import com.ttcsolutions.kwapp.commons.exception.BusinessException;
import com.ttcsolutions.kwapp.commons.util.ErrorCode;
import com.ttcsolutions.kwapp.core.model.entity.Device;
import com.ttcsolutions.kwapp.core.repository.DeviceRepository;
import com.ttcsolutions.kwapp.core.service.DeviceInternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceInternalService {
    private final DeviceRepository deviceRepository;

    @Override
    public Device getDevice0(String deviceCode) {
        return deviceRepository.findByDeviceCode(deviceCode)
                .orElseThrow(() -> new BusinessException(ErrorCode.DEVICE_NOT_FOUND,
                        "Device with code: " + deviceCode + " was not found"));
    }

}
