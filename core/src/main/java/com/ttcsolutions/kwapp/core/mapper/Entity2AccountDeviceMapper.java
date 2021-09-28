package com.ttcsolutions.kwapp.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ttcsolutions.kwapp.commons.mapper.BeanMapper;
import com.ttcsolutions.kwapp.core.model.entity.AccountDevice;
import com.ttcsolutions.kwapp.core.model.response.AccountDeviceResponse;

@Mapper
public interface Entity2AccountDeviceMapper extends BeanMapper<AccountDevice, AccountDeviceResponse> {
  Entity2AccountDeviceMapper INSTANCE = Mappers.getMapper(Entity2AccountDeviceMapper.class);
}
