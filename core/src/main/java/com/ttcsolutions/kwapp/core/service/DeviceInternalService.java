package com.ttcsolutions.kwapp.core.service;

import com.ttcsolutions.kwapp.core.model.entity.Device;

/**
 * Internal services for Device, return entity
 *
 * @author mahai
 */
public interface DeviceInternalService {
    /**
     * Get Device entity
     *
     * @param deviceCode
     * @return
     */
    Device getDevice0(String deviceCode);
}
